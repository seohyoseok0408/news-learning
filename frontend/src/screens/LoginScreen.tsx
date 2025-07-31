import React from 'react';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import styles from './LoginScreen.styles';

export default function LoginScreen() {
    return (
        <View style={styles.container}>
            {/* 앱 로고/제목 */}
            <Image
                source={require('../../assets/images/logos/news-learning-logo.png')}
                style={styles.logo}
            />

            <View style={styles.buttonContainer}>
                {/* 카카오 로그인 버튼 */}
                <TouchableOpacity>
                    <Image
                        source={require('../../assets/images/login/kakao-login-button.png')}
                        style={styles.loginButton}
                    />
                </TouchableOpacity>

                {/* 구글 로그인 버튼 */}
                <TouchableOpacity>
                    <Image
                        source={require('../../assets/images/login/google-login-button.png')}
                        style={styles.loginButton}
                    />
                </TouchableOpacity>
            </View>
        </View>
    );
}
