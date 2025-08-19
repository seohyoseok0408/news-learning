import React, { useState, useEffect } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    SafeAreaView,
    StatusBar,
    Image,
    Modal
} from 'react-native';
import { useNavigation, useRoute, RouteProp } from '@react-navigation/native';
import { quizApi } from '../api/QuizApi';
import styles from './QuizScreen.styles';

// 네비게이션 파라미터 타입
type QuizScreenParams = {
    articleId: number;
    version: 'ORIGINAL' | 'SHORT' | 'MIDDLE';
};

// 백엔드 API 응답 타입 정의
interface QuizQuestion {
    quizId: number;
    hanjaId: number;
    question: string;
    options: string[];
}

interface QuizResponse {
    message: string;
    data: QuizQuestion[];
}

export default function QuizScreen() {
    const navigation = useNavigation<any>();
    const route = useRoute<RouteProp<{ Quiz: QuizScreenParams }, 'Quiz'>>();
    const [selectedAnswer, setSelectedAnswer] = useState<number | null>(null);
    const [quizzes, setQuizzes] = useState<QuizQuestion[]>([]);
    const [showResultModal, setShowResultModal] = useState(false);
    const [isCorrect, setIsCorrect] = useState(false);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [loading, setLoading] = useState(true);

    // 컴포넌트 마운트 시 백엔드에서 퀴즈 데이터 가져오기
    useEffect(() => {
        loadQuizData();
    }, []);

    // 백엔드에서 퀴즈 데이터 가져오기
    const loadQuizData = async () => {
        try {
            setLoading(true);
            const { articleId, version } = route.params;
            const response = await quizApi.getArticleQuizzes(articleId, version);
            
            // 백엔드 응답 데이터를 그대로 사용
            setQuizzes(response.data);
            console.log('퀴즈 데이터 로드 성공:', response.data);
        } catch (error) {
            console.error('퀴즈 데이터 로드 실패:', error);
            // 에러 발생 시 빈 배열로 설정
            setQuizzes([]);
        } finally {
            setLoading(false);
        }
    };

    // 현재 문제 데이터
    const currentQuiz = quizzes[currentQuestionIndex];
    const progress = ((currentQuestionIndex + 1) / Math.max(quizzes.length, 1)) * 100;

    const handleAnswerSelect = (answerIndex: number) => {
        setSelectedAnswer(answerIndex);
        
        // 정답 여부 확인 (백엔드에서 정답 정보를 받아와야 함)
        // 임시로 첫 번째 옵션이 정답으로 가정
        const correct = answerIndex === 0;
        setIsCorrect(correct);
        
        // 결과 모달 표시
        setShowResultModal(true);
    };

    const handleNextQuestion = () => {
        setShowResultModal(false);
        setSelectedAnswer(null);
        
        if (currentQuestionIndex < quizzes.length - 1) {
            setCurrentQuestionIndex(currentQuestionIndex + 1);
        } else {
            // 마지막 문제면 퀴즈 종료
            navigation.goBack();
        }
    };

    const handleClose = () => {
        navigation.goBack();
    };

    // 로딩 중
    if (loading) {
        return (
            <SafeAreaView style={styles.container}>
                <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />
                <View style={styles.loadingContainer}>
                    <Text>퀴즈를 불러오는 중...</Text>
                </View>
            </SafeAreaView>
        );
    }

    // 퀴즈가 없는 경우
    if (quizzes.length === 0) {
        return (
            <SafeAreaView style={styles.container}>
                <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />
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
                <View style={styles.emptyContainer}>
                    <Text>이 기사에는 퀴즈가 없습니다.</Text>
                </View>
            </SafeAreaView>
        );
    }

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

            {/* 퀴즈 진행도 */}
            <View style={styles.progressContainer}>
                <Text style={styles.progressText}>
                    진행도
                </Text>
                <View style={styles.progressBar}>
                    <View 
                        style={[
                            styles.progressFill, 
                            { width: `${progress}%` }
                        ]} 
                    />
                </View>
            </View>

            {/* 퀴즈 질문 */}
            <View style={styles.questionContainer}>
                <Text style={styles.questionText}>{currentQuiz.question}</Text>
            </View>

            {/* 답변 옵션 */}
            <View style={styles.optionsContainer}>
                {currentQuiz.options.map((option, index) => (
                    <TouchableOpacity 
                        key={index}
                        style={styles.optionButton}
                        onPress={() => handleAnswerSelect(index)}
                        disabled={selectedAnswer !== null}
                    >
                        <Text style={styles.optionText}>{option}</Text>
                    </TouchableOpacity>
                ))}
            </View>

            {/* 결과 모달 */}
            <Modal
                visible={showResultModal}
                transparent={true}
                animationType="fade"
            >
                <View style={styles.modalOverlay}>
                    <View style={styles.modalContent}>
                        <Text style={[
                            styles.modalTitle,
                            isCorrect ? styles.correctText : styles.incorrectText
                        ]}>
                            {isCorrect ? '정답입니다!' : '오답입니다!'}
                        </Text>
                        <Text style={styles.modalMessage}>
                            {isCorrect ? '잘했습니다!' : '다시 한번 생각해보세요.'}
                        </Text>
                        <TouchableOpacity 
                            style={styles.modalButton}
                            onPress={handleNextQuestion}
                        >
                            <Text style={styles.modalButtonText}>
                                {currentQuestionIndex < quizzes.length - 1 ? '다음 문제' : '퀴즈 종료'}
                            </Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </SafeAreaView>
    );
}