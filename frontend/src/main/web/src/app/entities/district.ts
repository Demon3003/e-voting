import { Election } from "./election";
import { User } from "./user";

export interface District {

    id: number;

    number: string;

    city: string;

    address: string;

    elections?: Election[];

    users?: User[]

}