
export async function GET() {
    return Response.json(
        {
            method: "GET",
            message: "hello"

        }
    )
}

export async function POST() {
    return Response.json(
        {
            method: "POST",
            message: "hello"

        }
    )
}