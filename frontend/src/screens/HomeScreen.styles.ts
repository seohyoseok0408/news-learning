import { StyleSheet, Dimensions } from 'react-native';

const { width, height } = Dimensions.get('window');

export default StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#ffffff',
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 20,
        paddingVertical: 15,
        backgroundColor: '#ffffff',
    },
    logo: {
        width: 110,
        height: 28,
        resizeMode: 'contain',
    },
    headerIcons: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    iconButton: {
        marginLeft: 20,
    },
    icon: {
        width: 27,
        height: 27,
    },
    searchIcon: {
        width: 25,
        height: 25,
    },
    categoryContainer: {
        backgroundColor: '#ffffff',
        height: 70,
    },
    categoryContent: {
        paddingHorizontal: 20,
        paddingVertical: 10,
    },
    newsContainer: {
        paddingHorizontal: 20,
        paddingTop: 0,
    },
});