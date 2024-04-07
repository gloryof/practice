"use client"; 

import { useRouter } from "next/navigation";
import RegisterPage from "./RegisterPage";
import { RegisterUserResponse, registerUser } from "@/lib/api/register-user";
import { WebErrorResponse } from "@/lib/api/web-reseponse";

export default function Page() {
  const router = useRouter()
  const successFn = (res: RegisterUserResponse): void  => {
    alert("User Id [" + res.id + "]で登録されました")
  }
  const failFn = (res: WebErrorResponse): void => {
      alert("登録に失敗しました");
      router.push('/login')
  }
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
      .then((response) => {
          response
              .map(
                  successFn,
                  failFn
              )
      })
  }

  return <RegisterPage 
    registerUser={executeRegister}
    redirectToLogin={redirectToLogin}
  />
}