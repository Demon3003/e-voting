import { User } from "./user";

export interface Election {

    id: number;

    name: string;

    description?: string;

    startDate: string;

    endDate: string

    createdById: number;

    users?: User[];

}