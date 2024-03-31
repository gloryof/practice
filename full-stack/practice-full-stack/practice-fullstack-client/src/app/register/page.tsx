"use client"; 

import { useRouter } from "next/navigation";
import RegisterPage from "./RegisterPage";
import { registerUser } from "@/lib/api/register-user";

export default function Page() {
  const router = useRouter()
  const redirectToLogin = (event: React.MouseEvent<HTMLInputElement>) => {
    event.preventDefault();
    router.push('/login')
  }
  const executeRegister = (event: React.MouseEvent<HTMLInputElement>) => {
    event.preventDefault();
    registerUser({
      name: "test-user",
      password: "test-password",
      birthday: "1986-12-16"
    })
  }

  return <RegisterPage 
    registerUser={executeRegister}
    login={redirectToLogin}
  />
}