const API_BASE_URL = 'http://172.20.10.3:8080';

// 퀴즈 관련 타입 정의
export interface QuizOption {
    text: string;
}

export interface QuizQuestion {
    quizId: number;
    hanjaId: number;
    question: string;
    options: string[];
}

export interface QuizResponse {
    message: string;
    data: QuizQuestion[];
}

export interface QuizAnswerRequest {
    quizId: number;
    selectedOptionIndex: number;
}

export interface QuizAnswerResponse {
    message: string;
    data: {
        isCorrect: boolean;
        correctAnswer: number;
        explanation: string;
    };
}

// 퀴즈 API 클래스
class QuizApi {
    private baseUrl = API_BASE_URL;

    // 기사별 퀴즈 가져오기
    async getArticleQuizzes(articleId: number, version: 'ORIGINAL' | 'SHORT' | 'MIDDLE'): Promise<QuizResponse> {
        try {
            const url = `${this.baseUrl}/api/articles/${articleId}/quizzes?version=${version}`;
            console.log('퀴즈 가져오기 요청:', url);
            
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log('퀴즈 가져오기 응답 상태:', response.status);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('퀴즈 가져오기 응답 데이터:', data);
            
            return data;
        } catch (error) {
            console.error('퀴즈 가져오기 오류:', error);
            throw error;
        }
    }

    // 답변 제출 (필요시 구현)
    async submitQuizAnswer(request: QuizAnswerRequest): Promise<QuizAnswerResponse> {
        try {
            const url = `${this.baseUrl}/api/quiz/answer`;
            console.log('답변 제출 요청:', url, request);
            
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(request),
            });

            console.log('답변 제출 응답 상태:', response.status);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('답변 제출 응답 데이터:', data);
            
            return data;
        } catch (error) {
            console.error('답변 제출 오류:', error);
            throw error;
        }
    }
}

export const quizApi = new QuizApi();