import {  NextResponse } from 'next/server';
import { register, collectDefaultMetrics } from 'prom-client';


collectDefaultMetrics()
export async function GET() {
    const result = await register.metrics()
    return new NextResponse(result)
}
