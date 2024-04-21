import { NextRequest, NextResponse, userAgent } from "next/server"
import pino from "pino";


const logger = pino()
export function middleware(request: NextRequest) {
    const agent = userAgent(request)
    logger.info({
        level: "INFO",
        path: request.nextUrl.pathname,
        timestamp: new Date(),
        userAgent: agent.browser
    })
    return NextResponse.next();
}

export const config = {
    matcher: ["/api/:path*"]
};