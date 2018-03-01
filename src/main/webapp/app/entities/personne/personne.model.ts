import { BaseEntity } from './../../shared';

export class Personne implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numRpps?: string,
    ) {
    }
}
