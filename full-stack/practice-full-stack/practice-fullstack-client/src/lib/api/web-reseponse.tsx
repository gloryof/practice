
export class WebResponse<Result> {
    successResult: WebSuccessResponse<Result> | null
    errorResult: WebErrorResponse | null

    private constructor(
        success: WebSuccessResponse<Result> | null,
        error: WebErrorResponse | null
    ) {
        this.successResult = success,
        this.errorResult = error
    }

    static createSuccessRseponse<Res>(success: WebSuccessResponse<Res>): WebResponse<Res> {
        return new WebResponse<Res>(
            success,
            null
        )
    }

    static createErrorRseponse<Res>(error: WebErrorResponse): WebResponse<Res> {
        return new WebResponse<Res>(
            null,
            error
        )
    }

    isScucess(): boolean {
        return this.getResult() != null
    }

    getResult(): Result | undefined {
        return this.successResult?.result
    }

    getFail(): WebErrorResponse | undefined {
        return this.errorResult
    }

    isFail(): boolean {
        return this.getFail() != null
    }

    map(
        success: (response: Result) => void,
        fail: (resepnse: WebErrorResponse) => void
    ) {
        if (this.isSuccess()) {
            const response = this.successResult

            response ? success(response.result) : null
        } else {

            const result = this.errorResult

            result ? fail(result) : null
        }
    }

    private isSuccess(): boolean {
        return this.successResult != null
    }

    private isFail(): boolean {
        return this.errorResult != null
    }
}

export type WebSuccessResponse<Result> = {
    status: Number,
    result: Result
}

export type WebErrorResponse = {
    status: Number,
    result: WebErrorResponseDetail
}

export type WebErrorResponseDetail = {
    message: string
}