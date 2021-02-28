import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, Button,Dimensions,ImageBackground,Image } from 'react-native';
import { TextInput } from 'react-native-gesture-handler';
import firestore from '@react-native-firebase/firestore';
import auth from '@react-native-firebase/auth';
import ImagePicker from 'react-native-image-picker';

// const options = {
//     title: 'Select Avatar',
//     storageOptions: {
//       skipBackup: true,
//       path: 'images',
//     },
//   };
// const openPicker= () => {
//   ImagePicker.showImagePicker(options, (response) => {
//     // console.log('Response = ', response);
//     if (response.didCancel) {
//       console.log('User cancelled image picker');
//     } else if (response.error) {
//       console.log('ImagePicker Error: ', response.error);
//     } else {
//       const uri = response.uri;
//       this.setState({
//         selectedPictureUri: uri,
//       });
//     }
//   });
// }
/**
 * Profile screen
 */

const { width: WIDTH } = Dimensions.get('window')


export default class Profile2 extends React.Component {
    constructor(props){
        super(props);
        
        this.state =({
            // image: null,
            emailS:'',
            passwordS:'',
            nameS:'',
            phoneS:''
        })
    }

    // onSubmit = async () => {
    //     try {
    //       const post = {
    //         photo: this.state.image,
    //       }
    //       this.props.firebase.uploadPost(post)
      
    //       this.setState({
    //         image: null,
    //         title: '',
    //         description: ''
    //       })
    //     } catch (e) {
    //       console.error(e)
    //     }
    //   }

    static navigationOptions = ({ navigation }) => {
        return {
            title: navigation.getParam('name'),
        };
    };
    
    createUser =(emailS,passwordS,nameS,phoneS) => {
        if (!emailS.trim()) {
            alert('Please Enter email');
            return;
          }
        if (!passwordS.trim()) {
            alert('Please Enter password');
            return;
          }
          if (!nameS.trim()) {
            alert('Please Enter name');
            return;
          }
        if (!phoneS.trim()) {
            alert('Please Enter phone');
            return;
          }
        auth()
        .createUserWithEmailAndPassword(emailS,passwordS)
        .then(() => this.props.navigation.navigate('Profile'))
        .catch(error => {
          if (error.code === 'auth/email-already-in-use') {
            console.log('That email address is already in use!');
          }
      
          if (error.code === 'auth/invalid-email') {
            console.log('That email address is invalid!');
          }
          console.error(error);
        });
        firestore()
            .collection('Users')
            .add({
                email: emailS,
                password: passwordS,
                name: nameS,
                phone: phoneS
            })
            .then(() => {
                console.log('User added!');
            });
    }

    render() {

        const { navigate, state } = this.props.navigation;

        return (
            
            <ImageBackground source={require("../image/download.jpg")} style={styles.container}>
            <View style={styles.container}>
                {/* <Button 
                        title="Select Image"
                        onPress={openPicker}
                /> */}
                <TextInput 
                style={styles.Input}
                placeholder={'Email'}
                placeholderTextColo={'rgba(255,255,255,0.7)'}
                underlineColorAndroid='transparent'
                onChangeText={(emailS)=>this.setState({emailS})}
                />

                <TextInput 
                style={styles.Input}
                placeholder={'Password'}
                secureTextEntry={true}
                placeholderTextColo={'rgba(255,255,255,0.7)'}
                underlineColorAndroid='transparent'
                onChangeText={(passwordS)=>this.setState({passwordS})}
                />

                <TextInput 
                style={styles.Input}
                placeholder={'Name'}
                placeholderTextColo={'rgba(255,255,255,0.7)'}
                underlineColorAndroid='transparent'
                onChangeText={(nameS)=>this.setState({nameS})}
                />

                <TextInput 
                style={styles.Input}
                placeholder={'Phone Number'}
                placeholderTextColo={'rgba(255,255,255,0.7)'}
                underlineColorAndroid='transparent'
                onChangeText={(phoneS)=>this.setState({phoneS})}
                />

                <Button 
                        title="Register"
                        onPress={()=> this.createUser(this.state.emailS,this.state.passwordS,this.state.nameS,this.state.phoneS)}
                />



                <Button
                    title="Go to home screen"
                    onPress={() => navigate('Home')}
                />

            </View>
            </ImageBackground>
        );

    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    Input:{
        width: WIDTH -105,
        height: 45,
        borderRadius: 25,
        fontSize: 16,
        paddingLeft: 45,
        backgroundColor: 'rgba(0,0,0,0.35)',
        color: 'rgba(255,255,255,0.7)',
        marginHorizontal: 25,
    }
});