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
import NewsCard from '../components/NewsCard';
import CategoryTab from '../components/CategoryTab';
import BottomNavigation from '../navigation/BottomNavigation';
import styles from './HomeScreen.styles';

// 카테고리 데이터
const categories = ['전체', '정치', '북한', '경제', '마켓', '산업', '사회', '전국', '세계', '문화', '건강', '연예', '스포츠', '오피니언'];

// 뉴스 데이터 (임시)
const newsData = [
    {
        id: 1,
        category: '정치',
        title: '[속보] 李대통령, 광주·무안 공항이전 갈등에 "대통령 실에 TF 구성"',
        author: '임형섭 기자',
        timeAgo: '8시간 전',
        date: '2025/07/03',
        imageUrl: require('../../assets/images/samples/sample-news-image2.png')
    },
    {
        id: 2,
        category: '스포츠',
        title: '이정후, 4경기 만의 안타로 부진 탈출 신호탄...볼넷 도 1개 골라',
        author: '송고 기자',
        timeAgo: '14시간 전',
        date: '2025/07/03',
        imageUrl: require('../../assets/images/samples/sample-news-image.png')
    }
];

export default function HomeScreen() {
    const [selectedCategory, setSelectedCategory] = useState('전체');

    const handleCategoryPress = (category: string) => {
        setSelectedCategory(category);
    };

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
            >
                {newsData.map((news) => (
                    <NewsCard
                        key={news.id}
                        news={news}
                    />
                ))}
            </ScrollView>

            {/* 하단 네비게이션 */}
            <BottomNavigation currentTab="articles" />
        </SafeAreaView>
    );
}