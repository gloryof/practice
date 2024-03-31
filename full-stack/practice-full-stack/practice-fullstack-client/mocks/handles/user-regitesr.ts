import { HttpResponse } from "msw"
import { http } from `msw`

export const handlers = [
    http.post("/api/user/register", () => {
        return HttpResponse.json({
            id: "test-user-id"
        })
    })
]