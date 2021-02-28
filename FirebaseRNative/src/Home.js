import React, { useState, useEffect } from 'react';
import { StyleSheet,
     View, 
     Button,
     Dimensions,
     Text,
     ImageBackground,
     Label,
} from 'react-native';
import { TextInput } from 'react-native-gesture-handler';
import auth from '@react-native-firebase/auth';

/**
 * Home screen
 */
const { width: WIDTH } = Dimensions.get('window')

export default class Home extends React.Component{
constructor(props){
    super(props);
    
    this.state =({
        emailS:'',
        passwordS:''
    })
}



    login =(emailS,passwordS) => 
    {
        if (!emailS.trim()) {
            alert('Please Enter email');
            return;
          }
        if (!passwordS.trim()) {
            alert('Please Enter password');
            return;
          }
        auth()
        .signInWithEmailAndPassword(emailS,passwordS)
        .then(() => this.props.navigation.navigate('Profile'))
        .catch(error => {
            if (error.code === 'auth/user-not-found') {
              console.log('User not found!');
            }      
        console.error(error);
        });
    }

    render() {

        const { navigate } = this.props.navigation;
        return (
            <ImageBackground source={require("../image/download.jpg")} style={styles.container}>
                <View>
                    <TextInput 
                    style={styles.Input}
                    placeholder={'Username'}
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
                    <Button 
                        title="Login"
                        onPress={()=> this.login(this.state.emailS,this.state.passwordS)}
                    />
                    <Button
                        title="Register"
                        onPress={() => navigate(
                            'Register', { name: 'Register' }
                        )}
                    />
                </View>
                </ImageBackground>
        );

    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        width:'100%',
        height: '100%',
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