export type RootStackParamList = {
    Login: undefined;
    Home: undefined;
    ArticleDetail: { articleId?: number };  // 기사 ID를 파라미터로 받을 수 있음
    Quiz: undefined; // 추가
};