import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';

const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'name', headerName: 'Name', width: 70 },
    { field: 'value', headerName: 'value', width: 130 },
    { field: 'timeToMine', headerName: 'timeToMine', width: 130 },
];

const rows = [
    { id:1, name: "ore1", value: 10, timeToMine: 5 },
    { id:2, name: "ore2", value: 12, timeToMine: 3 },
    { id:3, name: "ore3", value: 14, timeToMine: 2 },
    { id:4,name: "ore4", value: 16, timeToMine: 8 },
    { id:5,name: "ore5", value: 18, timeToMine: 4 },
    { id:6,name: "ore6", value: 20, timeToMine: 6 },
];

export default function DataTable(props) {
    const [selectionModel, setSelectionModel] = React.useState([]);
    const handleData = props.handle;
    return (
        <div style={{ height: 400, width: '100%' }}>
            <DataGrid onSelectionModelChange={(newSelectionModel) => {
                setSelectionModel(newSelectionModel);
                let data = [];
                console.log(rows);
                console.log(newSelectionModel);
                rows.forEach((row, index) => {
                    newSelectionModel.forEach((item, i) => {
                        if (item === row.id) {
                            data.push(row);
                        }
                    })
                });
                handleData(data);
            }}
                rows={rows}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
                checkboxSelection
                      selectionModel={selectionModel}
            />
        </div>
    );
}