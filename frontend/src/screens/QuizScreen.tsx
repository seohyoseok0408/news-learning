import React, { useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    SafeAreaView,
    StatusBar,
    Image
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import styles from './QuizScreen.styles';

// 백엔드 API 응답 타입 정의
interface QuizOption {
    id: number;
    text: string;
    isCorrect: boolean;
}

interface QuizQuestion {
    id: number;
    question: string;
    options: QuizOption[];
}

interface QuizResponse {
    message: string;
    data: {
        quizId: number;
        currentQuestion: number;
        totalQuestions: number;
        question: QuizQuestion;
        progress: number;
    };
}

// 더미 퀴즈 데이터 (백엔드 연동 전까지 사용)
const quizData: QuizResponse = {
    message: "성공",
    data: {
        quizId: 1,
        currentQuestion: 1,
        totalQuestions: 4,
        question: {
            id: 1,
            question: "투기 억제는 무엇을 의미하는가?",
            options: [
                {
                    id: 1,
                    text: "실수요자의 주택 구매를 제한하는 정책",
                    isCorrect: false
                },
                {
                    id: 2,
                    text: "비정상적인 시세 차익을 노린 투자를 막기 위한 규제 정책",
                    isCorrect: true
                },
                {
                    id: 3,
                    text: "금융기관이 대출을 확대하는 정책",
                    isCorrect: false
                }
            ]
        },
        progress: 35
    }
};

export default function QuizScreen() {
    const navigation = useNavigation<any>();
    const [selectedAnswer, setSelectedAnswer] = useState<number | null>(null);

    const currentQuestion = quizData.data.question;
    const progress = quizData.data.progress;

    const handleAnswerSelect = (answerIndex: number) => {
        setSelectedAnswer(answerIndex);
        // TODO: 백엔드에 답변 전송
        console.log('선택된 답변:', answerIndex);
    };

    const handleClose = () => {
        navigation.goBack();
    };

    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />
            
            {/* 헤더 */}
            <View style={styles.header}>
                <TouchableOpacity onPress={handleClose}>
                    <Image
                        source={require('../../assets/images/icons/close-icon.png')}
                        style={styles.closeIcon}
                    />
                </TouchableOpacity>
                <Text style={styles.title}>뉴스 러닝 퀴즈</Text>
                <View style={styles.placeholder} />
            </View>

            {/* 퀴즈 질문 */}
            <View style={styles.questionContainer}>
                <Text style={styles.questionText}>{currentQuestion.question}</Text>
            </View>

            {/* 답변 옵션 */}
            <View style={styles.optionsContainer}>
                {currentQuestion.options.map((option, index) => (
                    <TouchableOpacity 
                        key={option.id}
                        style={styles.optionButton}
                        onPress={() => handleAnswerSelect(index)}
                    >
                        <Text style={styles.optionText}>{option.text}</Text>
                    </TouchableOpacity>
                ))}
            </View>

            {/* 진행도 */}
            <View style={styles.progressContainer}>
                <Text style={styles.progressLabel}>진행도</Text>
                <View style={styles.progressBar}>
                    <View style={[styles.progressFill, { width: `${progress}%` }]} />
                </View>
            </View>
        </SafeAreaView>
    );
}