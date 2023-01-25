// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyDF7z1ZmXuxd8vSZ7hQlRdoMEmT1udngKA",
  authDomain: "healmeadmin.firebaseapp.com",
  projectId: "healmeadmin",
  storageBucket: "healmeadmin.appspot.com",
  messagingSenderId: "351515149693",
  appId: "1:351515149693:web:a5e37abb9f4a981066f814"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const storage = getStorage(app);