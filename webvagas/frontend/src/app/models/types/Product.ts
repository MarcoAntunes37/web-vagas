import ClientDescriptions from "./ClientDescription";

export default interface Product {
    id: string,
    active: boolean,
    defaultPrice: string,
    clientPrice: number,
    clientDescriptions: ClientDescriptions[],
    name: string,
    created: string,
    updated: string
}