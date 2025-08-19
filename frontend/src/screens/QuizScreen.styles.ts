import { StyleSheet } from 'react-native';

export default StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#FFFFFF',
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 20,
        paddingVertical: 15,
    },
    closeIcon: {
        width: 20,
        height: 20,
        resizeMode: 'contain',
    },
    title: {
        fontSize: 22,
        color: '#0080FF',
        fontWeight: '900',
        marginLeft: 20,
    },
    placeholder: {
        width: 40,
    },
    questionContainer: {
        paddingHorizontal: 20,
        paddingVertical: 40,
        alignItems: 'center',
    },
    questionText: {
        fontSize: 24,
        color: '#2B1B17',
        fontWeight: '900',
        textAlign: 'center',
        lineHeight: 26,
    },
    optionsContainer: {
        paddingHorizontal: 20,
        paddingVertical: 20,
    },
    optionButton: {
        backgroundColor: '#FFFFFF',
        borderWidth: 3,
        borderColor: '#0080FF',
        borderRadius: 8,
        paddingVertical: 20,
        paddingHorizontal: 20,
        marginBottom: 22,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    optionText: {
        fontSize: 16,
        color: '#2B1B17',
        textAlign: 'center',
        lineHeight: 22,
        fontWeight: '900',
    },
    progressContainer: {
        position: 'absolute',
        bottom: 260,
        left: 20,
        right: 20,
    },
    progressLabel: {
        fontSize: 16,
        color: '#2B1B17',
        marginBottom: 20,
        textAlign: 'left',
        fontWeight: '900',
    },
    progressBar: {
        height: 8,
        backgroundColor: '#E5E5E5',
        borderRadius: 4,
        overflow: 'hidden',
    },
    progressFill: {
        height: '100%',
        backgroundColor: '#007AFF',
        borderRadius: 4,
    },
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: 'center',
        alignItems: 'center',
    },

    modalContent: {
        backgroundColor: '#ffffff',
        borderRadius: 12,
        padding: 24,
        marginHorizontal: 20,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },

    modalTitle: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 16,
        textAlign: 'center',
    },

    modalMessage: {
        fontSize: 16,
        color: '#666',
        marginBottom: 24,
        textAlign: 'center',
    },

    modalButton: {
        backgroundColor: '#0080FF',
        paddingHorizontal: 24,
        paddingVertical: 12,
        borderRadius: 8,
    },

    modalButtonText: {
        color: '#ffffff',
        fontSize: 16,
        fontWeight: '600',
    },

    correctText: {
        color: '#4CAF50',
    },

    incorrectText: {
        color: '#F44336',
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    loadingText: {
        fontSize: 16,
        color: '#2B1B17',
        fontWeight: '900',
    },
    emptyContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    emptyText: {
        fontSize: 16,
        color: '#2B1B17',
        fontWeight: '900',
    },
    progressText: {
        fontSize: 16,
        color: '#2B1B17',
        fontWeight: '900',
        marginBottom: 15,
        textAlign: 'left',
    },
});