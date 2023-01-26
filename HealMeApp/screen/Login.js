import React, { useCallback } from 'react';
import { View, Text, StyleSheet, Image, TextInput, TouchableOpacity } from 'react-native';
import { useFonts } from 'expo-font';
const Login = () => {

    let [fontsLoaded] = useFonts({
        'MonoSpace': require("../assets/Fonts/Space_Mono/SpaceMono-Regular.ttf")
      });
    
      const onLayoutRootView = useCallback(async () => {
        if (fontsLoaded) {
          await SplashScreen.hideAsync();
        }
      }, [fontsLoaded]);
    
      if (!fontsLoaded) {
        return null;
      }

    return (
       <View style={styles.container} onLayout={onLayoutRootView}>
            <Image style={styles.gambar} source={require("../assets/Picture2.png")}/>
            <Text style={styles.text}>Login</Text>
            <View style={styles.inputArea}>
                <TextInput style={styles.textInput} placeholder='Username' />
                <TextInput style={styles.textInput} placeholder='Password' />
            </View>
            <View style={styles.inputArea}>
                <TouchableOpacity style={styles.button}>
                    <Text style={{fontFamily: 'MonoSpace', color:"white"}}>Login</Text>
                </TouchableOpacity>
                <Text style={{ color: 'black', fontFamily: 'MonoSpace',}}>
                    Dont Have Account?   
                    <TouchableOpacity><Text style={{ color: 'dimgrey', fontFamily: 'MonoSpace',}}>  Sign Up</Text></TouchableOpacity>
                </Text>
            </View>
       </View>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff0E5',
        padding: 10,
        marginTop: 30,
        shadowOffset: {
            width: 10,
            height: 10,
        },
        shadowColor: 'black',
        shadowRadius: 10,
        shadowOpacity: 1,
        borderRadius: 20,
        borderColor: 'black',
        borderStyle: 'solid',
        borderWidth: 3,
        width: "80%",
        elevation: 30,
        fontFamily: 'MonoSpace',
    },
    inputArea: {
        alignItems: 'center',
        marginBottom: 20,
    },
    textInput: {
        width: 200,
        fontFamily: 'MonoSpace',
        backgroundColor: 'rgba(255, 255, 255, 0.3)',
        margin: 10,
        padding: 5,
        borderRadius: 15,
    }, 
    button: {
        padding: 10,
        fontFamily: 'MonoSpace',
        borderRadius: 10,
        width: '40%',
        alignItems: 'center',
        backgroundColor: 'firebrick',
        margin: 20,
        borderColor: 'black',
    },
    gambar: {
        height: 100,
        width: "100%",
        resizeMode:'contain'
    },
    text: {
        fontSize: 20,
        fontFamily: 'MonoSpace',
        textAlign: 'center',
    }
})

export default Login;
