export interface IAffiliate {
  id?: number;
  code?: string;
  name?: string;
}

export class Affiliate implements IAffiliate {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
