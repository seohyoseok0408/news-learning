import { StyleSheet, Dimensions } from 'react-native';

const { width } = Dimensions.get('window');

export default StyleSheet.create({
    container: {
        backgroundColor: '#0080FF',
        borderRadius: 12,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 5,
        },
        shadowOpacity: 0.4,
        shadowRadius: 3.84,
        elevation: 5,
    },
    image: {
        width: '100%',
        height: 200,
        borderTopLeftRadius: 12,
        borderTopRightRadius: 12,
        resizeMode: 'cover',
    },
    content: {
        padding: 15,
    },
    categoryTag: {
        backgroundColor: '#ffffff',
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 16,
        alignSelf: 'flex-start',
        marginBottom: 12,
    },
    categoryText: {
        color: '#2B1B17',
        fontSize: 16,
        fontWeight: '900',
    },
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#ffffff',
        lineHeight: 22,
        marginBottom: 8,
    },
    metaInfo: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    authorTime: {
        fontSize: 12,
        color: '#DADADA',
    },
    date: {
        fontSize: 12,
        color: '#DADADA',
    },
});