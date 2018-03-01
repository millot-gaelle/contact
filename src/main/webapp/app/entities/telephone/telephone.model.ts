import { BaseEntity } from './../../shared';

export const enum Type {
    'FIXE',
    'PORTABLE'
}

export class Telephone implements BaseEntity {
    constructor(
        public id?: number,
        public type?: Type,
        public numero?: string,
        public personne?: BaseEntity,
    ) {
    }
}
