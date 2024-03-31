import { useRouter } from "next/router"

const redirectToRegister = () => {
    const router = useRouter();

    router.push("/rgister")
}