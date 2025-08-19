import React, { useState, useEffect } from 'react';
import {
    View,
    Text,
    ScrollView,
    TouchableOpacity,
    Image,
    SafeAreaView,
    StatusBar,
    ActivityIndicator,
    Modal,
    Pressable,
    AccessibilityInfo,
    Button
} from 'react-native';
import { useRoute, RouteProp } from '@react-navigation/native';
import styles from './ArticleDetailScreen.styles';
import { useNavigation } from '@react-navigation/native';
import { newsAPI } from '../api/NewsApi';

// API 응답 타입 정의
interface HanjaWord {
    id: number;
    word: string;
    definition: string;
}

interface Summary {
    content: string;
    hanjaWords: HanjaWord[];
}

interface ArticleDetail {
    articleId: number;
    title: string;
    reporter: string;
    mediaName: string;
    imageUrl: string;
    publishedAt: string;
    summaries: {
        ORIGINAL: Summary;
        SHORT: Summary;
        MIDDLE: Summary;
    };
    more: Array<{
        id: number;
        title: string;
    }>;
}

interface ApiResponse {
    message: string;
    data: ArticleDetail;
}

// 요약 수준 타입 정의
type SummaryLevel = 'ORIGINAL' | 'SHORT' | 'MIDDLE';

export default function ArticleDetailScreen() {
    const route = useRoute<RouteProp<{ ArticleDetail: { articleId: number } }, 'ArticleDetail'>>();
    const { articleId } = route.params;

    const [article, setArticle] = useState<ArticleDetail | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isSaved, setIsSaved] = useState(false);
    const [currentSummaryLevel, setCurrentSummaryLevel] = useState<SummaryLevel>('MIDDLE');
    // 모달 관련 상태 추가
    const [modalVisible, setModalVisible] = useState(false);
    const [selectedHanja, setSelectedHanja] = useState<HanjaWord | null>(null);

    const navigation = useNavigation<any>();

    // 한자어 클릭 핸들러
    const handleHanjaPress = (hanja: HanjaWord) => {
        setSelectedHanja(hanja);
        setModalVisible(true);
    };

    // 모달 닫기 핸들러
    const handleCloseModal = () => {
        setModalVisible(false);
        setSelectedHanja(null);
    };

    // 기사 상세 정보 가져오기
    useEffect(() => {
        const fetchArticleDetail = async () => {
            try {
                setLoading(true);
                setError(null);

                console.log('🔵 기사 상세 로드 시작 - ID:', articleId);
                const articleData = await newsAPI.getArticleDetail(articleId); // API 호출
                console.log('✅ 기사 상세 로드 완료:', articleData.title);

                setArticle(articleData);
            } catch (err) {
                console.error('❌ 기사 상세 로드 실패:', err);
                setError('기사를 불러오는데 실패했습니다.');
            } finally {
                setLoading(false);
            }
        };

        fetchArticleDetail();
    }, [articleId]);

    // 한자어를 하이라이트하는 함수 수정
    const highlightHanjaWords = (text: string, hanjaWords: HanjaWord[]) => {
        // if (hanjaWords.length === 0) {
        //     return <Text style={styles.articleContent}>{text}</Text>;
        // }

        // 한자어를 찾아서 하이라이트 처리
        let highlightedText = text;
        const parts: Array<{ text: string; isHighlighted: boolean; hanja?: HanjaWord }> = [];

        // 각 한자어를 찾아서 분할
        hanjaWords.forEach((hanja) => {
            const regex = new RegExp(`(${hanja.word})`, 'g');
            const matches = highlightedText.match(regex);

            if (matches) {
                const splitText = highlightedText.split(regex);
                splitText.forEach((part, index) => {
                    if (part === hanja.word) {
                        parts.push({ text: part, isHighlighted: true, hanja: hanja });
                    } else if (part.trim()) {
                        parts.push({ text: part, isHighlighted: false });
                    }
                });
                return;
            }
        });
        console.log('parts', parts);
        // 한자어가 없으면 원본 텍스트 반환
        if (parts.length === 0) {
            return <Text style={styles.articleText}>{text}</Text>;
        }

        // 하이라이트된 텍스트 렌더링 - 정렬 문제 해결
        return (
            <Text style={styles.articleText}>
                {parts.map((part, index) => {
                    if (part.isHighlighted && part.hanja) {
                        return (
                
                                <Text key={index} style={styles.highlightedWord}
                                onPress={() => handleHanjaPress(part.hanja!)}>                                    {part.text}
                                </Text>
                        );
                    } else {
                        return <Text>{part.text}</Text>;
                    }
                })}
            </Text>
        );
    };

    const handleSummaryLevelChange = () => {
        // 요약 수준을 순환으로 변경
        setCurrentSummaryLevel((prevLevel) => {
            switch (prevLevel) {
                case 'MIDDLE':
                    return 'SHORT';
                case 'SHORT':
                    return 'ORIGINAL';
                case 'ORIGINAL':
                    return 'MIDDLE';
                default:
                    return 'MIDDLE';
            }
        });
    };

    const handleBack = () => {
        navigation.goBack();
    };

    const handleQuiz = () => {
        if (article) {
            navigation.navigate('Quiz', {
                articleId: article.articleId,
                version: currentSummaryLevel
            });
        }
    };

    const handleSave = () => {
        setIsSaved(!isSaved);
        console.log('저장 상태 변경:', !isSaved);
    };

    const handleRelatedArticle = (relatedArticleId: number) => {
        // 관련 기사 클릭 시 같은 화면으로 이동 (다른 ID)
        navigation.push('ArticleDetail', { articleId: relatedArticleId });
    };

    // 로딩 상태
    if (loading) {
        return (
            <SafeAreaView style={styles.container}>
                <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />
                <View style={styles.loadingContainer}>
                    <ActivityIndicator size="large" color="#007AFF" />
                    <Text style={styles.loadingText}>기사를 불러오는 중...</Text>
                </View>
            </SafeAreaView>
        );
    }

    // 에러 상태
    if (error || !article) {
        return (
            <SafeAreaView style={styles.container}>
                <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />
                <View style={styles.errorContainer}>
                    <Text style={styles.errorText}>{error || '기사를 불러올 수 없습니다.'}</Text>
                    <TouchableOpacity style={styles.retryButton} onPress={() => window.location.reload()}>
                        <Text style={styles.retryButtonText}>다시 시도</Text>
                    </TouchableOpacity>
                </View>
            </SafeAreaView>
        );
    }

    // 요약 수준에 따른 텍스트와 한자어 가져오기
    const currentSummary = article.summaries[currentSummaryLevel];
    const summaryLevelText = {
        'ORIGINAL': '원문',
        'SHORT': '짧음',
        'MIDDLE': '중간'
    };

    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />

            {/* 헤더 */}
            <View style={styles.header}>
                <TouchableOpacity onPress={handleBack} style={styles.backButton}>
                    <Image
                        source={require('../../assets/images/icons/back-arrow-icon.png')}
                        style={styles.backIcon}
                    />
                </TouchableOpacity>

                <View style={styles.headerButtons}>
                    <TouchableOpacity onPress={handleQuiz} style={styles.headerButton}>
                        <Text style={styles.headerButtonText}>퀴즈</Text>
                    </TouchableOpacity>
                    <TouchableOpacity onPress={handleSave} style={[styles.headerButton, isSaved && styles.savedButtonBorder]}>
                        <Text style={[
                            styles.headerButtonText,
                            isSaved && styles.savedButtonText,
                        ]}>
                            {isSaved ? '저장됨' : '저장'}
                        </Text>
                    </TouchableOpacity>
                </View>
            </View>

            {/* 기사 내용 */}
            <ScrollView style={styles.content} showsVerticalScrollIndicator={false}>
                {/* 기사 이미지 */}
                <Image source={{ uri: article.imageUrl }} style={styles.articleImage} />

                {/* 카테고리 및 요약 수준 */}
                <View style={styles.articleMeta}>
                    <View style={styles.categoryTag}>
                        <Text style={styles.categoryText}>{article.mediaName}</Text>
                    </View>
                    <View style={styles.summaryLevelContainer}>
                        <Text style={styles.summaryLevelLabel}>요약 수준</Text>
                        <TouchableOpacity
                            style={styles.summaryLevelButton}
                            onPress={handleSummaryLevelChange}
                        >
                            <Text style={styles.summaryLevelText}>{summaryLevelText[currentSummaryLevel]}</Text>
                        </TouchableOpacity>
                    </View>
                </View>

                {/* 기사 제목 */}
                <Text style={styles.articleTitle}>{article.title}</Text>

                {/* 작성자 및 시간 */}
                <Text style={styles.authorTime}>
                    {article.reporter} - {new Date(article.publishedAt).toLocaleDateString('ko-KR')}
                </Text>

                {/* 구분선 추가 */}
                <View style={styles.divider} />

                {/* 기사 본문 (한자어 하이라이트 적용) */}
                <View style={styles.articleContainer}>
                {highlightHanjaWords(currentSummary.content, currentSummary.hanjaWords)}
                </View>
                {/* 구분선 추가 */}
                <View style={styles.divider} />

                {/* 관련 기사 */}
                <View style={styles.relatedSection}>
                    <Text style={styles.relatedTitle}>다른 기사</Text>
                    {article.more.map((relatedArticle) => (
                        <TouchableOpacity
                            key={relatedArticle.id}
                            style={styles.relatedArticle}
                            onPress={() => handleRelatedArticle(relatedArticle.id)}
                        >
                            <Text style={styles.relatedArticleTitle}>{relatedArticle.title}</Text>
                        </TouchableOpacity>
                    ))}
                </View>
            </ScrollView>
            {/* 한자어 모달 추가 */}
            <Modal
                animationType="fade"
                transparent={true}
                visible={modalVisible}
                onRequestClose={handleCloseModal}
            >
                <TouchableOpacity
                    style={styles.modalOverlay}
                    activeOpacity={1}
                    onPress={handleCloseModal}
                >
                    <View style={styles.modalContent}>
                        <View style={styles.modalHeader}>
                            <Text style={styles.modalTitle}>{selectedHanja?.word}</Text>
                            <TouchableOpacity onPress={handleCloseModal} style={styles.closeButton}>
                                <Text style={styles.closeButtonText}>✕</Text>
                            </TouchableOpacity>
                        </View>
                        <View style={styles.modalBody}>
                            <Text style={styles.modalDefinition}>{selectedHanja?.definition}</Text>
                        </View>
                    </View>
                </TouchableOpacity>
            </Modal>
        </SafeAreaView>
    );
}