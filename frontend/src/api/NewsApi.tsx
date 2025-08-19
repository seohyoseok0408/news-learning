const API_BASE_URL = 'http://172.20.10.3:8080';

export interface NewsItem {
    id: number;
    title: string;
    imageUrl: string;
    category: string;
    reporter: string;
    publishedAt: string;
}

export interface NewsResponse {
    message: string;
    data: {
        articles: NewsItem[];
        hasNext: boolean;
        nextCursorPublishedAt: string | null;
    };
}

// 기사 상세 API 응답 타입 정의
export interface HanjaWord {
    id: number;
    word: string;
    definition: string;
}

export interface Summary {
    content: string;
    hanjaWords: HanjaWord[];
}

export interface ArticleDetail {
    articleId: number;
    title: string;
    reporter: string;
    mediaName: string;
    imageUrl: string;
    publishedAt: string;
    summaries: {
        ORIGINAL: Summary;
        SHORT: Summary;
        MIDDLE: Summary;
    };
    more: Array<{
        id: number;
        title: string;
    }>;
}

export interface ArticleDetailResponse {
    message: string;
    data: ArticleDetail;
}

// 백엔드 카테고리 코드 매핑
const CATEGORY_MAPPING: { [key: string]: string } = {
    '전체': 'ETC',
    '정치': 'POLITICS',
    '경제': 'ECONOMY',
    '사회': 'SOCIETY',
    '연예': 'CULTURE',
    '국제': 'WORLD',
    '스포츠': 'SPORTS'
};

export const newsAPI = {
    // 카테고리별 뉴스 조회 (커서 기반)
    getNewsByCategory: async (category: string, cursorPublishedAt?: string): Promise<NewsResponse> => {
        try {
            let url: string;
            const params = new URLSearchParams();
            
            if (cursorPublishedAt) {
                params.append('cursorPublishedAt', cursorPublishedAt);
            }
            
            if (category === '전체') {
                url = `${API_BASE_URL}/api/articles`;
            } else {
                const backendCategory = CATEGORY_MAPPING[category];
                if (!backendCategory) {
                    throw new Error(`알 수 없는 카테고리: ${category}`);
                }
                params.append('category', backendCategory);
                url = `${API_BASE_URL}/api/articles`;
            }
            
            if (params.toString()) {
                url += `?${params.toString()}`;
            }
            
            console.log('�� 카테고리별 뉴스 요청 URL:', url);
            
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: NewsResponse = await response.json();
            return data;
        } catch (error) {
            console.error('카테고리별 뉴스 조회 실패:', error);
            throw error;
        }
    },

    // 기사 상세 조회
    getArticleDetail: async (articleId: number): Promise<ArticleDetail> => {
        try {
            console.log('📡 getArticleDetail 함수 호출됨 - 기사 ID:', articleId, '시간:', new Date().toISOString());
            
            const url = `${API_BASE_URL}/api/articles/${articleId}`;
            console.log(' 기사 상세 요청 URL:', url, '시간:', new Date().toISOString());
            
            const response = await fetch(url);
            console.log('📡 기사 상세 응답 받음 - 상태:', response.status, '시간:', new Date().toISOString());
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data: ArticleDetailResponse = await response.json();
            console.log('✅ 기사 상세 데이터 파싱 완료 - 제목:', data.data.title, '시간:', new Date().toISOString());
            
            return data.data;
        } catch (error) {
            console.error('❌ 기사 상세 조회 실패:', error, '시간:', new Date().toISOString());
            throw error;
        }
    }
};