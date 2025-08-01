import React from 'react';
import { TouchableOpacity, Text } from 'react-native';
import styles from './CategoryTab.styles';

interface CategoryTabProps {
    title: string;
    isSelected: boolean;
    onPress: () => void;
}

export default function CategoryTab({ title, isSelected, onPress }: CategoryTabProps) {
    return (
        <TouchableOpacity 
            style={[styles.container, isSelected && styles.selectedContainer]}
            onPress={onPress}
        >
            <Text style={[styles.text, isSelected && styles.selectedText]}>
                {title}
            </Text>
        </TouchableOpacity>
    );
}