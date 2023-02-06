import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import { Block, Accordion, Text, theme, Button, Input } from 'galio-framework';
import React, { useEffect, useState } from 'react';
import {View, StyleSheet, Alert, ScrollView, Image, Dimensions, TextInput} from 'react-native';
import { AntDesign } from '@expo/vector-icons';
import { useDispatch, useSelector } from 'react-redux';
import localhostaddress from '../localhost';
import { AppStyles } from '../AppStyles';

const { width, height } = Dimensions.get("screen");

const ConsultationDetails = ({ navigation}) => {

    const dispatch = useDispatch();
    const dataConsult = useSelector(state => state.dataConsult)
    const dataPatient = useSelector(state => state.dataPatient);
    const dataDiagnose = useSelector(state => state.dataDiagnose);
    const [consultation, setConsultation] = useState({});
    const [dataArray, setDataArray] = useState([]);
    const [diagnose, setDiagnose] = useState({});
    const [prescription, setPrescription] = useState({})
    const [data, SetData] = useState({});
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('');


    const fetchConsultation = async () => {

        // if(dataConsult.status === "Waiting") {

        axios.get(`${localhostaddress}:8081/api/consult/${dataConsult.id}`, { 
            headers:{
                "Content-Type": "application/json",
                Authorization: await AsyncStorage.getItem('Authorization')
            }
        })
        .then(({ data }) => {
            console.log(data)
            setConsultation(data)
            let dataArr = [{  title: "Details", content: data.description }];
            setDataArray(dataArr);
            SetData({
                image: dataPatient.patientDetail.coverImage, 
                name: dataPatient.patientDetail.name,
                status: data.status,
                age: dataPatient.patientDetail.age,
                gender: dataPatient.patientDetail.gender,
                subject: data.subject,
                date: data.consultationDate
            })
        })
        .catch((error) => {
            console.log(error)
            Alert.alert('Something went wrong')
        });
    // }
    }

    const fetchDiagnose = async () => {
        if(dataConsult.status === "PROGRESS" || dataConsult.status === "COMPLETED"){

            axios.get(`${localhostaddress}:8081/api/diagnose/${dataConsult.id}`, { 
                headers:{
                    "Content-Type": "application/json",
                    Authorization: await AsyncStorage.getItem('Authorization')
                }
            })
            .then(({ data }) => {
                console.log(data)
                setDiagnose(data)
                let dataArr = {  title: "Diagnose", content: data.description }
                setDataArray(state => {
                    return [...state, dataArr]
                })
            })
            .catch((error) => {
                console.log(error)
                Alert.alert('Something went wrong')
            });
        }
    }

    const sendDiagnose = async () => {
        if (description.length <= 0 ) {
            Alert.alert('Please fill out the required fields.');
            return;
        }
        try {
          let { data } = await axios.post(`${localhostaddress}:8081/api/diagnose`, 
            { consultationId: dataConsult.id, description: description, status: status }, {
            headers: {
              "Content-Type": "application/json",
              Authorization: await AsyncStorage.getItem("Authorization")
            },
            })
            dispatch({ type: 'SET_DIAG', payload: data });
            navigation.navigate("Consultations");
            fetchDiagnose();
        } catch (error) {
          console.log(error)
          Alert.alert("Something went wrong");
        } 
      };

    const buttonDiagnose = () => {
        if(data.status === "Waiting"){
            return(
                <Block center>
                    <Block>
                        <Text style={styles.body}>Diagnose</Text>
                        <View
                            style={{
                            backgroundColor: "transparent",
                            borderColor: '#000000',
                            borderWidth: 1,
                            width: 325,
                            height: 100,
                            marginTop: 5,
                            borderRadius: 20
                            }}>
                            <TextInput
                            editable
                            placeholder='Description'
                            multiline
                            numberOfLines={5}
                            maxLength={400}
                            onChangeText={setDescription}
                            value={description}
                            style={{paddingLeft: 15, height: 100, width: AppStyles.textInputWidth.main}}
                            />
                        </View>
                        <Input
                            rounded
                            type='default'
                            placeholder='Status: hospital/medicine'
                            bgColor='transparent'
                            style={styles.InputContainer}
                            onChangeText={setStatus}
                            value={status}
                        />
                    </Block>
                    <Block>
                        <Button color={AppStyles.color.tint} onPress={() => sendDiagnose()}>Send Feedback</Button>
                    </Block>
                </Block>
            )
        } else if(data.status === "PROGRESS"){
            return(
                <Block>
                    {(diagnose.status === "hospital") ? 
                    <Button color={AppStyles.color.tint} onPress={() => {navigation.navigate("Hospitals")}}>Send Referral Hospital</Button>
                    : <Button color={AppStyles.color.tint} onPress={() => {navigation.navigate("Medicines")}}>Send Prescription</Button>
                    }
                </Block>
            )
        } else{
            return(
                <Block>
                    <Button color={AppStyles.color.tint} disabled>{(diagnose.status === "hospital") ? "Completed Send Referral Hospital" : "Completed Send Prescription" }</Button>
                </Block>
            )
        }
    }
    
    useEffect(() => {
        fetchConsultation();
        fetchDiagnose();
    }, [])

    const convertDate = (date) => {
        const newDate = new Date(date);
        const parseDate = newDate.toDateString().split(' ').slice(1);
        const finalDate = `${parseDate[1]} ${parseDate[0]} ${parseDate[2]}`;
        return finalDate;
    }

    return (
        <Block center>
        <ScrollView
            style={styles.components}
            showsVerticalScrollIndicator={false}>
            <Block flex center>
            <Block flex style={styles.group}>
                <Text bold size={12} style={[styles.titleConsultation, styles.leftTitle]}>Consultation</Text>
                <Block row={true} card flex style={[styles.product, styles.shadow]}>
                    <Block flex style={[styles.imageContainer, styles.shadow]}>
                        <Image source={{ uri: data.image }} style={styles.horizontalImage} />
                    </Block>
                    <Block center flex space="between" style={styles.productDescription}>
                        <Text bold size={14} style={styles.productTitle}>{data.name}</Text>
                        <Text size={14} style={styles.productTitle} >{data.age} years old</Text>
                        <Text size={14} style={styles.productTitle} >{data.gender}</Text>
                    </Block>
                </Block>
                <Block >
                    <Block center>
                        <Block row>
                        <Text size={12} style={styles.productTitle}>{data.subject}</Text>
                        <Text size={12} color={(data.status === "PROGRESS") ? theme.COLORS.PRIMARY : (data.status === "COMPLETED") ? "#45DF31" : theme.COLORS.TWITTER} 
                        style={styles.statusTitle}
                        >{data.status}</Text>
                        </Block>
                    </Block>
                </Block>
                <Block center  row>
                    <AntDesign name="calendar" size={24} color="black" />
                    <Text style={styles.comment}>{convertDate(data.date)}</Text>
                </Block >
                <Block center style={{ height: 200 }}>
                    <Accordion dataArray={dataArray} />
                </Block>
                <Block center style={{marginTop: 10}}>{buttonDiagnose()}</Block>
            </Block>
        </Block>
    </ScrollView>
    </Block>
    );
}

const styles = StyleSheet.create({
    group: {
        paddingTop: theme.SIZES.BASE * 0.5,
      },
      product: {
        backgroundColor: theme.COLORS.WHITE,
        marginVertical: theme.SIZES.BASE,
        borderWidth: 0,
        minHeight: 114,
      },
      shadow: {
      shadowColor: theme.COLORS.BLACK,
      shadowOffset: { width: 0, height: 2 },
      shadowRadius: 4,
      shadowOpacity: 0.1,
      elevation: 2,
      },
      productTitle: {
        flex: 1,
        flexWrap: 'wrap',
        paddingHorizontal: 10,
        paddingVertical: 10
      },
      statusTitle: {
        flex: 1,
        flexWrap: 'wrap',
        paddingHorizontal: 10,
        paddingVertical: 10
      },
      imageContainer: {
        elevation: 1,
      },
      horizontalImage: {
        height: 122,
        width: 'auto',
        borderRadius: 10
      },
      components: {
        width: width
      },
      title: {
        fontSize: AppStyles.fontSize.title,
        fontWeight: 'bold',
        color: AppStyles.color.tint,
        marginTop: 20,
        marginBottom: 5,
      },
      titleConsultation: {
        fontSize: AppStyles.fontSize.content,
        fontWeight: 'bold',
        color: AppStyles.color.categoryTitle,
        marginTop: 20,
        marginBottom: 5,
      },
      leftTitle: {
        alignSelf: 'stretch',
        textAlign: 'left',
        marginLeft: 20,
      },
      comment: {
        fontSize: AppStyles.fontSize.normal,
        fontWeight: 'normal',
        color: "black",
        marginTop: 5,
        marginBottom: 20,
        width: 300,
        flexWrap: 'wrap',
        alignSelf: 'stretch',
        textAlign: 'left',
        marginLeft: 20,
      },
      body: {
        marginTop: 10,
        marginBottom: 5,
        paddingLeft: 20,
        paddingRight: 20,
        color: "black",
        fontWeight:'bold'
      },
      InputContainer: {
        width: 325,
        marginTop: 30,
      },
})

export default ConsultationDetails;
