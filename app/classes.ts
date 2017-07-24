export class Task{
    task_id: number;
    creator_user_id: number;
    name: string;
    responsible_user_id: number;
    description: string;
}

export class User{
    user_id: number;
    name: string;
    surname: string;
    telephone: string;
    email: string;
    gender: string;
    datebirth: string;
}