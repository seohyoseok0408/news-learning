import { StyleSheet } from 'react-native';

export default StyleSheet.create({
    container: {
        flexDirection: 'row',
        backgroundColor: '#ffffff',
        borderTopWidth: 1,
        borderTopColor: '#e9ecef',
        paddingBottom: 10,
        paddingTop: 10,
    },
    tab: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    icon: {
        width: 24,
        height: 24,
        tintColor: '#6c757d',
        marginBottom: 8,
        resizeMode: 'contain',
    },
    activeIcon: {
        tintColor: '#000000',
    },
    text: {
        fontSize: 12,
        color: '#6c757d',
        fontWeight: '900'
    },
    activeText: {
        color: '#000000',
        fontWeight: '900',
    },
});