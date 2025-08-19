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
    }
};