#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/ml.hpp>
#include <iostream>
#include <opencv2/opencv.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <fstream>
#include "Supp.h"

using namespace std;
using namespace cv;
using namespace cv::ml;
int run(int argc, char** argv);
int showImage(int argc, char** argv);
int argc;
char** argv;
const int	noOfImagePerCol = 1, noOfImagePerRow = 3; // create window partition 
int			winI = 0, winRows, winCols, object1 = 0, object2 = 0, x;
Mat			srcI, largeWin, win[noOfImagePerRow * noOfImagePerCol];
Mat			legend[noOfImagePerRow * noOfImagePerCol], tt;
const char  *fruits[6] = { "Apple", "Avocado", "Banana", "Lemon", "Orange", "Pear" };

int main() {
	//Intro Page : Tew Xhin Yee
	int option;

	cout << "Welcome To Fruit Recognition System \n";
	cout << "***********************************************\n";
	cout << "Enter 1 to Run the Process: \n";
	cout << "(1) Run Process \n";
	cout << "(2) Show Sample Image \n";
	cout << "***********************************************\n";
	cout << "Which step option you want to choose: ";
	cin >> option;
	switch (option)
	{
	case 1: run(argc, argv);
		break;
	case 2: showImage(argc, argv);
		break;
	default:
		cout << "Error \n";
		break;
	}
	system("pause");
	return 0;
}

int run(int argc, char ** argv) {
	//Get Image and generate Hu Moments : Foo Kein Sheng
	cout << "Fruit Image Recognition Process \n\n";

	vector<String> filenames;
	String folder = "C:/Users/user/Desktop/Project430/Project430/Inputs"; //Sample Images
	glob(folder, filenames, true); //Get the folder and load in the filename

	// Set up 2D array for training data
	float ary[468][6];
	float ary2[312][6];


	for (int i = 0; i < filenames.size(); i++) //Each images go Gray and Hu
	{
		srcI = imread(filenames[i], IMREAD_GRAYSCALE);
		winRows = srcI.rows, winCols = srcI.cols;
		winI = 0;
		createWindowPartition(srcI, largeWin, win, legend, noOfImagePerCol, noOfImagePerRow);

		srcI.copyTo(win[winI]);
		putText(legend[winI++], "Original", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);

		threshold(srcI, win[winI], 108, 255, THRESH_OTSU);
		putText(legend[winI++], "threshold", Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);

		win[1].copyTo(tt);

		// generate Hu moments here.
		Moments 	mom;
		double		hu[7];

		mom = moments(tt, true); // get moments of the thresholded image in win[1]
		HuMoments(mom, hu); // generate the Hu moments
		if (i % 10 > 5) {
			for (int k = 1; k < 7; k++) {
				ary2[object1][k - 1] = hu[k];

			}
			object1++;
		}
		else {
			for (int k = 1; k < 7; k++) {
				ary[object2][k - 1] = hu[k];
			}
			object2++;
		}
	}

	//train data
	int labels[468];
	int k = 0;
	for (int a = 0; a < 468; a++) {
		if (a % 78 == 0) {
			k++;
		}
		labels[a] = k;
	}


	Mat labelsMat(468, 1, CV_32SC1, labels);
	Mat trainingDataMat(468, 6, CV_32FC1, ary);

	// Section 2: Do normalization of the data
	// Xnew = (X - mean) / sigma
	// Section 2 : Tew Xhin Yee
	vector<double> mean, sigma;
	for (int i = 0; i < trainingDataMat.cols; i++) {  //take each of the features in vector
		Scalar meanOut, sigmaOut;
		meanStdDev(trainingDataMat.col(i), meanOut, sigmaOut);  //get mean and std deviation
		mean.push_back(meanOut[0]);
		sigma.push_back(sigmaOut[0]);
	}
	for (int i = 0; i < trainingDataMat.cols; i++) {
		trainingDataMat.col(i) = (trainingDataMat.col(i) - mean[i]) / sigma[i];
	}

	// Section 3: Train the SVM
	// Section 3: Ooi Yi Ling
	Ptr<SVM> svm = SVM::create();
	svm->setType(SVM::C_SVC);
	svm->setKernel(SVM::LINEAR);
	svm->setTermCriteria(TermCriteria(TermCriteria::MAX_ITER, 100, 1e-6));
	svm->train(trainingDataMat, ROW_SAMPLE, labelsMat);

	// Section 4: check the training and validating data
	// Section 4: Lam Kean Chin
	Mat			input;

	cout << "Test on training data";
	x = 0;
	for (int j = 0; j < 468; j++) { // Test on training data
		if (j % 78 == 0) {
			cout << "\n\n";
			cout << fruits[x];
			x++;
		}
		if (j % 26 == 0) {
			cout << "\n";
		}
		cout << svm->predict(trainingDataMat.row(j)) << ' ';
	}
	cout << "\n\n";
	cout << "Finish Training \n\n";
	system("pause");

	x = 0;
	cout << "\nTest on validating data" << endl;
	for (int j = 0; j < 312; j++) {
		if (j % 52 == 0) {
			cout << "\n\n";
			cout << fruits[x];
			x++;
		}
		if (j % 26 == 0) {
			cout << "\n\n";
		}
		for (int k = 0; k < 6; k++) {
			// normalize all features using the same mean and sigma from section 2
			ary2[j][k] = (ary2[j][k] - mean[k]) / sigma[k];
		}
		input = (Mat_<float>(1, 6) << ary2[j][0], ary2[j][1], ary2[j][2], ary2[j][3], ary2[j][4], ary2[j][5]);
		cout << svm->predict(input) << ' ';
	}
	cout << "\n\n";
	cout << "1.Apple 2.Avocado 3.Banana \n4.Lemon 5.Orange 6.Pear\n\n";

	system("pause");
	return 0;
}

int showImage(int argc, char ** argv) {
	// Show Image Page : Ooi Yi Ling
	int option;
	const int	noOfSampleImagePerCol = 1, noOfSampleImagePerRow = 1; // create window partition 
	char		filename[6][128];
	int			winI = 0, rows, cols;
	char		name[128];
	int x = 0;
	Mat src;
	std::fstream fs;

	fs.open("ImageSample/SampleFiles.txt", std::fstream::in);
	for (int j = 0; j < 6; j++)
		fs >> filename[j];
	for (int p = 0; p < 6; p++) {
		srcI = imread(filename[p]);
		if (srcI.empty()) {
			cout << "cannot open " << filename << endl;
			return -1;
		}
		rows = srcI.rows, cols = srcI.cols;
		winI = 0;
		createWindowPartition(srcI, largeWin, win, legend, noOfSampleImagePerCol, noOfSampleImagePerRow);

		srcI.copyTo(win[winI]);
		putText(legend[winI++], fruits[x], Point(5, 11), 1, 1, Scalar(250, 250, 250), 1);

		sprintf_s(name, "Input %d", p);
		imshow(name, largeWin);
		x++;
		waitKey();
	}
	run(argc, argv);
	waitKey();
	return 0;
}
