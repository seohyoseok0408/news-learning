import { StyleSheet } from 'react-native';

export default StyleSheet.create({
    container: {
        backgroundColor: '#ffffff',
        borderWidth: 2,
        borderColor: '#dee2e6',
        borderRadius: 16,
        paddingHorizontal: 12,
        paddingVertical: 6,  // 4에서 6으로 늘림
        marginRight: 8,
        height: 32,  // 28에서 32로 늘림
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 6 },
        shadowOpacity: 0.15,
        shadowRadius: 3.84,
        elevation: 5,
    },
    selectedContainer: {
        backgroundColor: '#007AFF',
        borderColor: '#007AFF',
    },
    text: {
        fontSize: 16,  // 13에서 12로 줄임
        color: '#000000',
        fontWeight: '900',
        textAlign: 'center',
        lineHeight: 16,  // lineHeight 추가
    },
    selectedText: {
        color: '#ffffff',
    },
});