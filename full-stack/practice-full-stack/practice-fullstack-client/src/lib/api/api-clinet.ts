import { WebErrorResponse, WebErrorResponseDetail, WebResponse, WebSuccessResponse } from "./web-reseponse";

export class ApiClient {
    private host: string;
    private port: Number;


    static createApiClient(): ApiClient {
        return new ApiClient("localhost", 8080)
    }

    private constructor(host: string, port: Number) {
        this.host = host
        this.port = port
    }

    async post<Req, Res>(
        { path, request }: 
        {
            path: string,
            request: Req
        }
    ): Promise<WebResponse<Res>> {
        return fetch(
            this.createPath(path),
            {
                method: "POST",
                body: JSON.stringify(request),
                headers: new Headers(
                    { "Content-Type": "application/json" }
                )
            }
        )
            .then(async(response) => {
                if (response.ok) {
                    
                    const detail: Res = await response.json()
                    const successResponse: WebSuccessResponse<Res> = {
                        status: response.status,
                        result: detail
                    }
                    return WebResponse.createSuccessRseponse(successResponse)
                } else {

                    const responesDetail: WebErrorResponseDetail = await response.json()
                    const errorResponse: WebErrorResponse = {
                        status: response.status,
                        result: responesDetail
                    }
                    return WebResponse.createErrorRseponse(errorResponse)
                }
            })
    }

    private createPath(path: string): string {
        return "http://" + this.host + ":" + this.port + path
    }
}
