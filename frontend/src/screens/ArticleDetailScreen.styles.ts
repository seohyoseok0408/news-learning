import { StyleSheet, Dimensions } from "react-native";

const { width, height } = Dimensions.get("window");

export default StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#ffffff",
  },
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingHorizontal: 20,
    paddingVertical: 15,
    borderBottomWidth: 1,
    borderBottomColor: "#e9ecef",
  },
  backButton: {
    padding: 5,
  },
  backIcon: {
    width: 20,
    height: 20,
    resizeMode: "contain",
  },
  headerButtons: {
    flexDirection: "row",
    alignItems: "center",
  },
  headerButton: {
    marginLeft: 15,
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 10,
    backgroundColor: "#FFFFFF",
    borderWidth: 2,
    borderColor: "#2B1B17",
  },
  headerButtonText: {
    fontSize: 14,
    color: "#2B1B17",
    fontWeight: "800",
  },
  savedButtonText: {
    color: "#0080FF",
  },
  savedButtonBorder: {
    borderColor: "#0080FF",
  },
  content: {
    flex: 1,
  },
  articleImage: {
    width: "100%",
    height: height * 0.25,
    resizeMode: "cover",
  },
  articleMeta: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingHorizontal: 20,
    paddingVertical: 15,
  },
  categoryTag: {
    backgroundColor: "#0080FF",
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 16,
  },
  categoryText: {
    color: "#ffffff",
    fontSize: 18,
    fontWeight: "600",
  },
  summaryLevelContainer: {
    flexDirection: "row",
    alignItems: "center",
    gap: 8,
  },
  summaryLevelLabel: {
    fontSize: 14,
    color: "#6c757d",
  },
  summaryLevelButton: {
    backgroundColor: "#ffffff",
    borderWidth: 1,
    borderColor: "#2B1B17",
    borderRadius: 12,
    paddingHorizontal: 8,
    paddingVertical: 4,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
  summaryLevelText: {
    fontSize: 14,
    color: "#2B1B17",
    fontWeight: "500",
  },
  articleTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#2B1B17",
    lineHeight: 28,
    paddingHorizontal: 20,
    paddingBottom: 10,
  },
  articleContainer: {
    paddingHorizontal: 20,
    paddingBottom: 30,
    borderWidth: 4,
    borderColor: "#0080FF",
    borderRadius: 10,
    padding: 15,
    backgroundColor: "#FFFFFF",
    marginHorizontal: 15,
    marginBottom: 20,
    flexDirection: "row",
    justifyContent: "flex-start",
    alignContent: "flex-start",
  },
  articleText: {
    fontSize: 16,
    color: "#2B1B17",
    lineHeight: 24,
    fontWeight: "600",
    includeFontPadding: false,
  },
  highlightedWord: {
    color: "#0080FF",
    textDecorationLine: "underline",
    fontWeight: "600",
  },
  relatedSection: {
    paddingHorizontal: 20,
    paddingBottom: 30,
  },
  relatedTitle: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#2B1B17",
    marginBottom: 15,
  },
  relatedArticle: {
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: "#7B7B7B",
  },
  relatedArticleTitle: {
    fontSize: 15,
    color: "#2B1B17",
    lineHeight: 20,
    fontWeight: "800",
  },
  authorTime: {
    fontSize: 14,
    color: "#6c757d",
    paddingHorizontal: 20,
    paddingBottom: 20,
  },
  divider: {
    height: 3,
    backgroundColor: "#7B7B7B",
    marginHorizontal: 0,
    marginBottom: 20,
  },
  hanjaSection: {
    marginBottom: 20,
  },
  hanjaTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 15,
    color: "#333",
  },
  hanjaItem: {
    marginBottom: 15,
    padding: 15,
    backgroundColor: "#F8F9FA",
    borderRadius: 8,
  },
  hanjaWord: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#007AFF",
    marginBottom: 5,
  },
  hanjaDefinition: {
    fontSize: 14,
    color: "#666",
    lineHeight: 20,
  },
  errorContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
  },
  errorText: {
    fontSize: 16,
    color: "#FF3B30",
    textAlign: "center",
    marginBottom: 20,
  },
  retryButton: {
    backgroundColor: "#007AFF",
    paddingHorizontal: 20,
    paddingVertical: 12,
    borderRadius: 8,
  },
  retryButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "500",
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  loadingText: {
    marginTop: 15,
    fontSize: 16,
    color: "#666",
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    justifyContent: "center",
    alignItems: "center",
  },

  modalContent: {
    backgroundColor: "#fff",
    borderRadius: 12,
    padding: 0,
    width: "85%",
    maxWidth: 350,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 8,
  },

  modalHeader: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 20,
    paddingBottom: 15,
  },

  modalTitle: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#007AFF",
    flex: 1,
  },

  closeButton: {
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: "#007AFF",
    justifyContent: "center",
    alignItems: "center",
  },

  closeButtonText: {
    fontSize: 14,
    color: "#fff",
    fontWeight: "bold",
  },

  modalBody: {
    paddingHorizontal: 20,
    paddingBottom: 20,
  },

  modalDefinition: {
    fontSize: 14,
    lineHeight: 20,
    color: "#333",
    marginTop: 5,
  },
});
