export interface IBranchOffice {
  id?: number;
  number?: string;
  name?: string;
  status?: string;
  affiliateCode?: string;
  affiliateId?: number;
}

export class BranchOffice implements IBranchOffice {
  constructor(
    public id?: number,
    public number?: string,
    public name?: string,
    public status?: string,
    public affiliateCode?: string,
    public affiliateId?: number
  ) {}
}
