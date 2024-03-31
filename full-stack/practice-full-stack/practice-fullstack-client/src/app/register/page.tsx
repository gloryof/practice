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
  const executeRegister = (formData: FormData) => {
    registerUser({
      name: formData.get("name") as string || "",
      password: formData.get("password") as string || "",
      birthday: formData.get("birthday") as string || "",
    })
  }

  return <RegisterPage 
    registerUser={executeRegister}
    login={redirectToLogin}
  />
}