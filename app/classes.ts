export class Task{
    taskId: number;
    status: string;
    creatorUserId: number;
    name: string;
    responsibleUserId: number;
    description: string;
}

export class User{
    userId: number;
    name: string;
    surname: string;
    telephone: string;
    email: string;
    gender: string;
    birth: string;
}

export class LoginUser {
    email: string;
    password: string;
    rememberMe: boolean;
}

export class Account {
    name: string;
}