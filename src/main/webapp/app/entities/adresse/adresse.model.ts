import { BaseEntity } from './../../shared';

export class Adresse implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: number,
        public complNum?: string,
        public rue?: string,
        public codePostal?: string,
        public ville?: string,
        public pays?: string,
        public personne?: BaseEntity,
    ) {
    }
}
