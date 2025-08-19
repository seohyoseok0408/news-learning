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
    // 전체 뉴스 조회
    getAllNews: async (): Promise<NewsItem[]> => {
        try {
            const response = await fetch(`${API_BASE_URL}/api/articles`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: NewsResponse = await response.json();
            return data.data.articles;
        } catch (error) {
            console.error('뉴스 조회 실패:', error);
            throw error;
        }
    },

    // 카테고리별 뉴스 조회
    getNewsByCategory: async (category: string): Promise<NewsItem[]> => {
        try {
            let url: string;
            
            if (category === '전체') {
                url = `${API_BASE_URL}/api/articles`;
            } else {
                const backendCategory = CATEGORY_MAPPING[category];
                if (!backendCategory) {
                    throw new Error(`알 수 없는 카테고리: ${category}`);
                }
                url = `${API_BASE_URL}/api/articles?category=${backendCategory}`;
            }
            
            console.log('🔵 API 요청 URL:', url);
            
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: NewsResponse = await response.json();
            return data.data.articles;
        } catch (error) {
            console.error('카테고리별 뉴스 조회 실패:', error);
            throw error;
        }
    }
};