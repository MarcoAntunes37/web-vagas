import { EmploymentType } from "../../models/enum/EmploymentType";
import Product from "../../models/types/Product";

const countryListOptions = [
    { code: 'AF', name: 'Afeganistão' },
    { code: 'AL', name: 'Albânia' },
    { code: 'DE', name: 'Alemanha' },
    { code: 'AD', name: 'Andorra' },
    { code: 'AO', name: 'Angola' },
    { code: 'AR', name: 'Argentina' },
    { code: 'AM', name: 'Armênia' },
    { code: 'AU', name: 'Austrália' },
    { code: 'AT', name: 'Áustria' },
    { code: 'BE', name: 'Bélgica' },
    { code: 'BO', name: 'Bolívia' },
    { code: 'BR', name: 'Brasil' },
    { code: 'CA', name: 'Canadá' },
    { code: 'CL', name: 'Chile' },
    { code: 'CN', name: 'China' },
    { code: 'CO', name: 'Colômbia' },
    { code: 'KR', name: 'Coreia do Sul' },
    { code: 'CU', name: 'Cuba' },
    { code: 'DK', name: 'Dinamarca' },
    { code: 'EG', name: 'Egito' },
    { code: 'AE', name: 'Emirados Árabes Unidos' },
    { code: 'EC', name: 'Equador' },
    { code: 'ES', name: 'Espanha' },
    { code: 'US', name: 'Estados Unidos' },
    { code: 'FR', name: 'França' },
    { code: 'GR', name: 'Grécia' },
    { code: 'GT', name: 'Guatemala' },
    { code: 'IN', name: 'Índia' },
    { code: 'ID', name: 'Indonésia' },
    { code: 'IQ', name: 'Iraque' },
    { code: 'IE', name: 'Irlanda' },
    { code: 'IL', name: 'Israel' },
    { code: 'IT', name: 'Itália' },
    { code: 'JP', name: 'Japão' },
    { code: 'MX', name: 'México' },
    { code: 'MZ', name: 'Moçambique' },
    { code: 'NG', name: 'Nigéria' },
    { code: 'NL', name: 'Países Baixos' },
    { code: 'PA', name: 'Panamá' },
    { code: 'PY', name: 'Paraguai' },
    { code: 'PE', name: 'Peru' },
    { code: 'PL', name: 'Polônia' },
    { code: 'PT', name: 'Portugal' },
    { code: 'GB', name: 'Reino Unido' },
    { code: 'RU', name: 'Rússia' },
    { code: 'SN', name: 'Senegal' },
    { code: 'RS', name: 'Sérvia' },
    { code: 'ZA', name: 'África do Sul' },
    { code: 'SE', name: 'Suécia' },
    { code: 'CH', name: 'Suíça' },
    { code: 'TH', name: 'Tailândia' },
    { code: 'TR', name: 'Turquia' },
    { code: 'UA', name: 'Ucrânia' },
    { code: 'UY', name: 'Uruguai' },
    { code: 'VE', name: 'Venezuela' },
    { code: 'VN', name: 'Vietnã' },
    { code: 'ZW', name: 'Zimbábue' }
];

const employmentTypeOptions = [
    { value: EmploymentType.FULLTIME, label: 'Período integral' },
    { value: EmploymentType.PARTTIME, label: 'Meio período' },
    { value: EmploymentType.CONTRACTOR, label: 'Contratante' },
    { value: EmploymentType.INTERN, label: 'Estagiario' }
]

const productListOptions: Product[] = [
    {
        id: 'prod_S866Rn1WSNGxxv',
        active: true,
        defaultPrice: 'price_1RDpuf2VoRElKCJv1X389cgT',
        clientPrice: 9.9,
        clientDescriptions: [
            {
                "icon": "check",
                "text": "Até 5 vagas todos os dias."
            },
            {
                "icon": "check",
                "text": "Envio de vagas 1x ao dia."
            },
            {
                "icon": "cancel",
                "text": "Prioridade nas vagas: Receba com antecedência."
            }
        ],
        name: 'Essentials',
        created: '2025-04-14 16:31:12+00',
        updated: '2025-04-14 16:31:13+00'
    },
    {
        id: 'prod_S867rCJc3K7ObN',
        active: true,
        defaultPrice: 'price_1RDpv22VoRElKCJvmybyo75h',
        clientPrice: 14.9,
        clientDescriptions: [
            {
                "icon": "check",
                "text": "Até 10 vagas todos os dias."
            },
            {
                "icon": "check",
                "text": "Envio de vagas 2x ao dia."
            },
            {
                "icon": "check",
                "text": "Prioridade nas vagas: Receba com antecedência."
            }
        ],
        name: 'Turbo',
        created: '2025-04-14 16:31:36+00',
        updated: '2025-04-14 16:31:36+00'
    }
]

export {
    countryListOptions,
    employmentTypeOptions,
    productListOptions
}