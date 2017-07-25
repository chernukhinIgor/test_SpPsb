export class Task{
    taskId: number;
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