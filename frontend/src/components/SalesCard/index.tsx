import axios from "axios";
import { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { Sale } from "../../models/sale";
import { BASE_URL } from "../../utils/request";
import NotificationButton from "../NotificationButton";
import "./styles.css";

function Salescard() {

    const min = new Date(new Date().setDate(new Date().getDate() - 365));
    const max = new Date();

    const [minDate, setMinDate] = useState(min);
    const [maxDate, setMaxDate] = useState(max);

    const [sales, setSales] = useState<Sale[]>([])

    useEffect(() => {

        const dmin = minDate.toISOString().slice(0, 10);
        const dmax = maxDate.toISOString().slice(0, 10);
        axios.get(`${BASE_URL}/sales?minDate=${dmin}&maxDate=${dmax}`) //endereço da requisição do Postman
            .then(response => {
                setSales(response.data.content);
            });
    }, [minDate, maxDate]);

    return (
        <div className="dsmeta-card">
            <h2 className="dsmeta-sales-title">Vendas</h2>
            <div>
                <div className="dsmeta-form-control-container">
                    <DatePicker
                        selected={minDate}
                        onChange={(date: Date) => setMinDate(date)}
                        className="dsmeta-form-control"
                        dateFormat="dd/MM/yyyy"
                    />
                </div>
                <div className="dsmeta-form-control-container">
                    <DatePicker
                        selected={maxDate}
                        onChange={(date: Date) => setMaxDate(date)}
                        className="dsmeta-form-control"
                        dateFormat="dd/MM/yyyy"
                    />
                </div>
            </div>

            <div>
                <table className="dsmeta-sales-table">
                    <thead>
                        <tr>
                            <th className="show992">ID</th>
                            <th className="show576">Data</th>
                            <th>Vendedor</th>
                            <th className="show992">Visitas</th>
                            <th className="show992">Vendas</th>
                            <th>Total</th>
                            <th>Notificar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            sales.map(sale => {
                                return (
                                    <tr key={sale.id}>
                                        <td className="show992">{sale.id}</td>
                                        <td className="show576">{new Date(sale.date).toLocaleDateString()}</td>
                                        <td>{sale.sellerName}</td>
                                        <td className="show992">{sale.visited}</td>
                                        <td className="show992">{sale.deals}</td>
                                        <td>R$ {sale.amount.toFixed(2)}</td>
                                        <td>
                                            <div className="dsmeta-red-btn-container">
                                                <NotificationButton saleId={sale.id} />
                                            </div>
                                        </td>
                                    </tr>
                                )
                            })
                        }
                    </tbody>

                </table>
            </div>

        </div>
    )
}

export default Salescard;









/*
ANOTAÇÕES
=========

LINHA 19 = a requisição axios.get acima retorna obj especial do JavaScript
           que se chama Promisse. E este objeto executa operação e pode falhar ou dar certo. 
           Para esta função dar certo: CHAMA uma função (.then). Esta função chama O objeto da resposta
           que deu certo (response), e podemos tratá-lo dentro da função como acima.
           Sendo assim: Aqui neste caso, o frontend faz uma requisição ao backend, que é prontamente atendida
           através do console.log
LINHA 22 = toISOString().slice(0, 10) -> especifica a formatação da data, e corta(slice) de-até onde vão as 
           formatos das datas aqui, por exemplo: 2021-07-13(dez caracteres)

LINHA 29 = [minDate, maxDate] - configurando useffect para processar requisição sempre que 
                                as datas mínima e máxima mudarem


LINHA 66 = (.map) - percorre a lista, e faz uma operação com cada elemento da lista.
LINHA 68 = (.key nomecriado.id) - O react exige que: Quando se faz uma renderização de conteúdo, baseado
           numa lista [item, lista, iendnd, iasf, 1], tenho de colocar um cada elemento o atributo chamdo KEY.
           Tenho de colocar um valor único para esta KEY, ou seja: Um id. Aqui no exemplo, usamos o próprio id 
           da venda. Que aqui é (sale).

LINHA 74 = toFixed(2) - coloca dois dígitos, nas casas decimais, no valor que não tinha no valor

*/