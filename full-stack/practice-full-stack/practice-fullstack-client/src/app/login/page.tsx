"use client";

import { useRouter } from "next/navigation";
import LoginPage from "./LoginPage";
import { getCsrfToken } from "next-auth/react"

export default async function Page() {
  const router = useRouter()
  const redirectToRegister = (event: React.MouseEvent<HTMLInputElement>) => {
    event.preventDefault();
    router.push('/register')
  }
  const csrfToken = await getCsrfToken()

  return <LoginPage
    redirectToRegister={redirectToRegister}
    csrfToken={csrfToken ?? ""}
  />
}