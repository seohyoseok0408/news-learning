import React from 'react';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../types/navigation';
import styles from './LoginScreen.styles';

type LoginScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Login'>;

export default function LoginScreen() {
    const navigation = useNavigation<LoginScreenNavigationProp>();

    const handleKakaoLogin = () => {
        // 임시로 HomeScreen으로 이동
        navigation.navigate('Home');
    };

    const handleGoogleLogin = () => {
        // 임시로 HomeScreen으로 이동
        navigation.navigate('Home');
    };

    return (
        <View style={styles.container}>
            {/* 앱 로고/제목 */}
            <Image
                source={require('../../assets/images/logos/news-learning-logo.png')}
                style={styles.logo}
            />

            <View style={styles.buttonContainer}>
                {/* 카카오 로그인 버튼 */}
                <TouchableOpacity onPress={handleKakaoLogin}>
                    <Image
                        source={require('../../assets/images/login/kakao-login-button.png')}
                        style={styles.loginButton}
                    />
                </TouchableOpacity>

                {/* 구글 로그인 버튼 */}
                <TouchableOpacity onPress={handleGoogleLogin}>
                    <Image
                        source={require('../../assets/images/login/google-login-button.png')}
                        style={styles.loginButton}
                    />
                </TouchableOpacity>
            </View>
        </View>
    );
}