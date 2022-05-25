// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBdxsOEvmfz3jkVB8UNIDqLFc1dnIzsQuY",
  authDomain: "cmpe275-14045.firebaseapp.com",
  projectId: "cmpe275-14045",
  storageBucket: "cmpe275-14045.appspot.com",
  messagingSenderId: "515464892456",
  appId: "1:515464892456:web:581d9ae546d80db94d251d"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const storage_bucket = getStorage(app);
