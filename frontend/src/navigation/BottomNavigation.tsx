import React from 'react';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import styles from './BottomNavigation.styles';

interface BottomNavigationProps {
    currentTab: string;
}

export default function BottomNavigation({ currentTab }: BottomNavigationProps) {
    const tabs = [
        { id: 'articles', title: '기사', icon: require('../../assets/images/icons/news-article-icon.png') },
        { id: 'bookmark', title: '북마크', icon: require('../../assets/images/icons/bookmark-icon.png') },
        { id: 'leaderboard', title: '리더보드', icon: require('../../assets/images/icons/leaderboard-icon.png') },
        { id: 'radio', title: '라디오', icon: require('../../assets/images/icons/radio-icon.png') },
    ];

    return (
        <View style={styles.container}>
            {tabs.map((tab) => (
                <TouchableOpacity 
                    key={tab.id}
                    style={styles.tab}
                    onPress={() => console.log(`Navigate to ${tab.title}`)}
                >
                    <Image 
                        source={tab.icon}
                        style={[
                            styles.icon,
                            currentTab === tab.id && styles.activeIcon
                        ]}
                    />
                    <Text style={[
                        styles.text,
                        currentTab === tab.id && styles.activeText
                    ]}>
                        {tab.title}
                    </Text>
                </TouchableOpacity>
            ))}
        </View>
    );
}