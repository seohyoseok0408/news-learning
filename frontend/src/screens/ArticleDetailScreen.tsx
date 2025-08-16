import React, { useState } from 'react';
import { 
    View, 
    Text, 
    ScrollView, 
    TouchableOpacity, 
    Image, 
    SafeAreaView,
    StatusBar 
} from 'react-native';
import styles from './ArticleDetailScreen.styles';
import { useNavigation } from '@react-navigation/native';

// 요약 수준 타입 정의 (백엔드와 맞춤)
type SummaryLevel = '원문' | '짧음' | '중간';

// 강조된 단어 타입 정의
interface HighlightedWord {
    word: string;
    meaning: string;
}

// 더미 데이터
const articleData = {
    id: 1,
    category: '경제',
    summaryLevel: '중간' as SummaryLevel,
    title: "'영골·갭투자' 틀어막아...年소득 2억 차주, 대출 14억→6억",
    author: '신연수 기자',
    timeAgo: '8시간 전',
    imageUrl: require('../../assets/images/samples/sample-news-image3.png'),
    content: `정부는 2025년 6월 28일부터 수도권 주택담보대출(주담대)을 최대 6억 원으로 제한하는 강력한 금융 규제를 시행한다. 소득과 주택 가격에 관계없이 일괄 적용되며, 이재명 정부의 첫 부동산 대책이다.

다주택자는 추가 주담대가 전면 금지되고, 전세보증금을 활용한 갭 투자도 차단된다. 주담대를 받은 사람은 6개월 내 전입이 의무이며, 1주택자는 기존 주택을 6개월 내 처분해야 한다.

생활안정자금 목적 대출은 1억 원까지만 가능하며, 주담대 만기는 30년 이하, 신용대출은 연 소득 이내로 제한된다. 정부는 가계대출 총량도 20조 원 감축할 계획으로, 전체 금융권에서 대출받기 어려워질 전망이다.

이번 정책은 문재인 정부의 세금 중심 대책과 달리, 금융규제를 통한 투기 억제에 초점을 맞췄다. 실수요 중심 시장 재편과 자금의 생산적 부문 유도가 목표지만, 무주택자까지 위축될 수 있다는 우려도 있다. 

정부는 공급 대책은 향후 시장 안정 후 추진하겠다고 
밝혔다. 금융위원회는 “무리한 대출을 통한 투기를 차단하겠다”고 강조했다.`,
    highlightedWords: [
        {
            word: "주택담보대출",
            meaning: "주택을 담보로 제공하고 받는 대출"
        },
        {
            word: "다주택자",
            meaning: "2개 이상의 주택을 소유한 사람"
        },
        {
            word: "전세보증금",
            meaning: "전세 계약 시 임대인에게 지급하는 보증금"
        },
        {
            word: "갭투자",
            meaning: "전세보증금과 주택담보대출의 차이를 이용한 투자"
        },
        {
            word: "금융 규제",
            meaning: "금융 시장의 안정성을 위한 정부의 제한 조치"
        },
        {
            word: "투기 억제",
            meaning: "투기적 행위를 막는 정책"
        },
        {
            word: "생활안정자금",
            meaning: "생활에 필요한 자금을 지원하는 대출"
        },
        {
            word: "신용대출",
            meaning: "담보 없이 신용만으로 받는 대출"
        }
    ],
    relatedArticles: [
        {
            id: 2,
            title: '이종석 국정원장 "지난날 책임질 부분 진솔하게 털어내야"'
        },
        {
            id: 3,
            title: "'해군 최초 승전'...해군 1함대, 옥계지구전투 전승 기념행사"
        },
        {
            id: 4,
            title: '과천시, "6·25 참전 기념비" 이전...전쟁 75주년 기념식도'
        }
    ]
};

export default function ArticleDetailScreen() {
    const [isSaved, setIsSaved] = useState(false);
    const [currentSummaryLevel, setCurrentSummaryLevel] = useState<SummaryLevel>('중간');  // 기본값 "중간"

    const navigation = useNavigation<any>();

    
    const handleSummaryLevelChange = () => {
        // 요약 수준을 순환으로 변경
        setCurrentSummaryLevel((prevLevel) => {
            switch (prevLevel) {
                case '중간':
                    return '짧음';
                case '짧음':
                    return '원문';
                case '원문':
                    return '중간';
                default:
                    return '중간';
            }
        });
    };

    

    const handleBack = () => {
        // 뒤로가기 로직
        console.log('뒤로가기');
    };

    const handleQuiz = () => {
        navigation.navigate('Quiz');
    };


    const handleSave = () => {
        // 저장 버튼 로직
        setIsSaved(!isSaved);
        console.log('저장 상태 변경:', !isSaved);
    };

    const handleRelatedArticle = (articleId: number) => {
        // 관련 기사 클릭 로직
        console.log('관련 기사 클릭:', articleId);
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
                <Image source={articleData.imageUrl} style={styles.articleImage} />
                
                {/* 카테고리 및 요약 수준 */}
                <View style={styles.articleMeta}>
                    <View style={styles.categoryTag}>
                        <Text style={styles.categoryText}>{articleData.category}</Text>
                    </View>
                    <View style={styles.summaryLevelContainer}>
                        <Text style={styles.summaryLevelLabel}>요약 수준</Text>
                        <TouchableOpacity 
                            style={styles.summaryLevelButton}
                            onPress={handleSummaryLevelChange}
                        >
                            <Text style={styles.summaryLevelText}>{currentSummaryLevel}</Text>
                        </TouchableOpacity>
                    </View>
                </View>
                
                {/* 기사 제목 */}
                <Text style={styles.articleTitle}>{articleData.title}</Text>
                
                {/* 작성자 및 시간 */}
                <Text style={styles.authorTime}>
                    {articleData.author} - {articleData.timeAgo}
                </Text>

                {/* 구분선 추가 */}
                <View style={styles.divider} />
                
                {/* 기사 본문 */}
                <Text style={styles.articleContent}>{articleData.content}</Text>

                {/* 구분선 추가 */}
                <View style={styles.divider} />
                
                {/* 관련 기사 */}
                <View style={styles.relatedSection}>
                    <Text style={styles.relatedTitle}>다른 기사</Text>
                    {articleData.relatedArticles.map((article) => (
                        <TouchableOpacity 
                            key={article.id}
                            style={styles.relatedArticle}
                            onPress={() => handleRelatedArticle(article.id)}
                        >
                            <Text style={styles.relatedArticleTitle}>{article.title}</Text>
                        </TouchableOpacity>
                    ))}
                </View>
            </ScrollView>
        </SafeAreaView>
    );
}