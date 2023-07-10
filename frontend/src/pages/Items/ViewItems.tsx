import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useCallback, useEffect, useState } from "react";
import { Item } from "../../types/item";
import { ItemService } from "../../services/item.service";
import { Typography, Paper, Fab } from "@mui/material";
import Modal from '@mui/material/Modal';
import Stack from '@mui/material/Stack';
import { CreateMeleeWeapon } from "./CreateMeleeWeapon"
import AddIcon from "@mui/icons-material/Add";

export const ViewItems = () => {

  const [items, setItems] = useState<Item[]>([]);
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const fetchItemsHandler = useCallback(async () => {
    console.log("Fetching items : ");
    let itemJSON = localStorage.getItem("items");
    if (itemJSON === null) {
      await ItemService.getAllItems()
      .then((items) => {
        itemJSON = JSON.stringify(items);
        localStorage.setItem("items", itemJSON);
        setItems(items);
        console.log(items)
      })
      .catch((e) => alert("Error fetching items: " + e));
    }
    setItems(itemJSON === null ? null : JSON.parse(itemJSON));
  }, []);

  const columns: GridColDef[] = [
    { field: 'name', headerName: 'Name', width: 200, editable: true },
    { field: 'strengthGroup', headerName: 'SG', width: 20 },
    { field: 'damage', headerName: 'Damage', width: 80 },
    { field: 'itemType', headerName: 'Type', width: 200 },
    { field: 'price', headerName: 'Price', width: 60 },
    { field: 'weight', headerName: 'Weight', width: 80 },
    { field: 'length', headerName: 'Length', width: 60 },
    { field: 'bepStorage', headerName: 'Storage', width: 60 },
    { field: 'bp', headerName: 'BV', width: 60 },
    { field: 'handGrip', headerName: 'Hands', width: 120 },
  ];

  useEffect(() => {
    fetchItemsHandler().then();
  }, [fetchItemsHandler]);


  const itemRows = items
    .map((item, index) => ({
      id: index + 1,
      name: item.itemKey,
      strengthGroup: item.strengthGroup,
      damage: item.damage,
      itemType: item.itemType,
      price: item.price,
      weight: item.weight,
      length: item.length,
      bepStorage: item.bepStorage,
      bp: item.bp,
      handGrip: item.handGrip,
    }));
  return (
    <>
      <Stack direction="row">
        <Fab color="success" size="small" aria-label="add">
          <AddIcon onClick={handleOpen}/>
        </Fab>
      </Stack>
      <div style={{ height: 400, width: '100%' }}>
        <DataGrid density={"compact"} rows={itemRows} columns={columns} autoHeight />
      </div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Paper elevation={3}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Create a new Melee Weapon
          </Typography>
          <CreateMeleeWeapon onConfirm={handleClose} />
        </Paper>
      </Modal>
    </>)

}