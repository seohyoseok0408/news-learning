import React from 'react';
import { View, Text, Image, TouchableOpacity } from 'react-native';
import styles from './NewsCard.styles';
import { useNavigation } from '@react-navigation/native';

interface NewsItem {
    id: number;
    category: string;
    title: string;
    author: string;
    timeAgo: string;
    date: string;
    imageUrl: any;
}

interface NewsCardProps {
    news: NewsItem;
}

export default function NewsCard({ news }: NewsCardProps) {
    const navigation = useNavigation<any>(); // 임시로 any 타입 사용

    const handlePress = () => {
        navigation.navigate('ArticleDetail', { articleId: news.id });
    };

    return (
        <TouchableOpacity style={styles.container} onPress={handlePress}>
            <Image source={news.imageUrl} style={styles.image} />
            
            <View style={styles.content}>
                <View style={styles.categoryTag}>
                    <Text style={styles.categoryText}>{news.category}</Text>
                </View>
                
                <Text style={styles.title} numberOfLines={2}>
                    {news.title}
                </Text>
                
                <View style={styles.metaInfo}>
                    <Text style={styles.authorTime}>
                        {news.author} · {news.timeAgo}
                    </Text>
                    <Text style={styles.date}>{news.date}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );
}