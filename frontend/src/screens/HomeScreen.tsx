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
    RefreshControl
} from 'react-native';
import NewsCard from '../components/NewsCard';
import CategoryTab from '../components/CategoryTab';
import BottomNavigation from '../navigation/BottomNavigation';
import { newsAPI, NewsItem } from '../api/NewsApi';
import styles from './HomeScreen.styles';

// 카테고리 데이터 (백엔드와 매핑)
const categories = ['전체', '정치', '경제', '사회', '연예', '국제', '스포츠'];

export default function HomeScreen() {
    const [selectedCategory, setSelectedCategory] = useState('전체');
    const [newsData, setNewsData] = useState<NewsItem[]>([]);
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState<string | null>(null);

    // 뉴스 데이터 로드
    const loadNews = async (category: string = selectedCategory) => {
        try {
            setLoading(true);
            setError(null);
            
            console.log('🔵 선택된 카테고리:', category);
            const news = await newsAPI.getNewsByCategory(category);
            setNewsData(news);
            console.log('✅ 로드된 뉴스 개수:', news.length);
        } catch (err) {
            console.error('뉴스 로드 실패:', err);
            setError('뉴스를 불러오는데 실패했습니다.');
        } finally {
            setLoading(false);
        }
    };

    // 새로고침
    const onRefresh = async () => {
        setRefreshing(true);
        await loadNews();
        setRefreshing(false);
    };

    // 카테고리 변경 시 뉴스 로드
    const handleCategoryPress = async (category: string) => {
        console.log('🔵 카테고리 변경:', category);
        setSelectedCategory(category);
        await loadNews(category);
    };

    // 초기 데이터 로드
    useEffect(() => {
        loadNews();
    }, []);

    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="dark-content" backgroundColor="#ffffff" />

            {/* 앱 헤더 */}
            <View style={styles.header}>
                <Image
                    source={require('../../assets/images/logos/news-learning-small-logo.png')}
                    style={styles.logo}
                />
                <View style={styles.headerIcons}>
                    <TouchableOpacity style={styles.iconButton}>
                        <Image
                            source={require('../../assets/images/icons/search-icon.png')}
                            style={styles.searchIcon}
                        />
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.iconButton}>
                        <Image
                            source={require('../../assets/images/icons/default-user-icon.png')}
                            style={styles.icon}
                        />
                    </TouchableOpacity>
                </View>
            </View>

            {/* 카테고리 탭 */}
            <ScrollView
                horizontal
                showsHorizontalScrollIndicator={false}
                style={styles.categoryContainer}
                contentContainerStyle={styles.categoryContent}
            >
                {categories.map((category) => (
                    <CategoryTab
                        key={category}
                        title={category}
                        isSelected={selectedCategory === category}
                        onPress={() => handleCategoryPress(category)}
                    />
                ))}
            </ScrollView>

            {/* 뉴스 목록 */}
            <ScrollView
                style={styles.newsContainer}
                showsVerticalScrollIndicator={false}
                refreshControl={
                    <RefreshControl
                        refreshing={refreshing}
                        onRefresh={onRefresh}
                    />
                }
            >
                {loading ? (
                    <View style={styles.loadingContainer}>
                        <ActivityIndicator size="large" color="#007AFF" />
                        <Text style={styles.loadingText}>뉴스를 불러오는 중...</Text>
                    </View>
                ) : error ? (
                    <View style={styles.errorContainer}>
                        <Text style={styles.errorText}>{error}</Text>
                        <TouchableOpacity style={styles.retryButton} onPress={() => loadNews()}>
                            <Text style={styles.retryButtonText}>다시 시도</Text>
                        </TouchableOpacity>
                    </View>
                ) : newsData.length === 0 ? (
                    <View style={styles.emptyContainer}>
                        <Text style={styles.emptyText}>뉴스가 없습니다.</Text>
                    </View>
                ) : (
                    newsData.map((news) => (
                        <NewsCard
                            key={news.id}
                            news={{
                                id: news.id,
                                category: news.category,
                                title: news.title,
                                author: news.reporter,
                                timeAgo: getTimeAgo(news.publishedAt),
                                date: formatDate(news.publishedAt),
                                imageUrl: news.imageUrl
                            }}
                        />
                    ))
                )}
            </ScrollView>

            {/* 하단 네비게이션 */}
            <BottomNavigation currentTab="articles" />
        </SafeAreaView>
    );
}

// 유틸리티 함수들
function getTimeAgo(publishedAt: string): string {
    const now = new Date();
    const published = new Date(publishedAt);
    const diffInHours = Math.floor((now.getTime() - published.getTime()) / (1000 * 60 * 60));
    
    if (diffInHours < 1) return '방금 전';
    if (diffInHours < 24) return `${diffInHours}시간 전`;
    
    const diffInDays = Math.floor(diffInHours / 24);
    return `${diffInDays}일 전`;
}

function formatDate(publishedAt: string): string {
    const date = new Date(publishedAt);
    return date.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}