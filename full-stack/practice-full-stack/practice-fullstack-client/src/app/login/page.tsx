"use client";

import { useRouter } from "next/navigation";
import LoginPage from "./LoginPage";
import { loginUser } from "@/lib/api/login-user";

export default function Page() {
  const router = useRouter()
  const redirectToRegister = (event: React.MouseEvent<HTMLInputElement>) => {
    event.preventDefault();
    router.push('/register')
  }
  const executeLogin = (formData: FormData) => {
    loginUser({
      userId: formData.get("user-id") as string || "",
      password: formData.get("password") as string || "",
    })
  }

  return <LoginPage
    loginUser={executeLogin} 
    redirectToRegister={redirectToRegister}
  />
}