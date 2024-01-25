import React, {useEffect, useState} from 'react';
import {FormControlLabel, FormGroup, Checkbox, Typography} from "@mui/material";
import {Item} from "../../types/item";

type FiltersChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => void;
export interface FilterIProps {
    items: Item[]; // Assuming Item is a defined interface for your items
    setFilteredItems: React.Dispatch<React.SetStateAction<Item[]>>;
    t: (key: string) => string;
}

export const ArmorFilters = ({ items, setFilteredItems, t } : FilterIProps) => {

    const [filters, setFilters] = useState({
        HEAD: false,
        CHEST: false,
        STOMACH: false,
        LEFT_LEG: false,
        RIGHT_LEG: false,
        LEFT_ARM: false,
        RIGHT_ARM: false,
    });

    const handleFiltersChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilters({ ...filters, [event.target.name]: event.target.checked });
    };

    useEffect(() => {
        // Check if any filter is selected
        const isAnyFilterSelected = Object.values(filters).some(value => value);

        if (!isAnyFilterSelected) {
            setFilteredItems(items);
        } else {
            // Convert filters object to an array of selected body parts
            const selectedFilters = Object.entries(filters).filter(([_, value]) => value).map(([key]) => key);

            const newFilteredItems = items.filter(item => {
                // Ensure item.bodyPartsCovered is defined
                if (!item.bodyPartsCovered) {
                    return false; // If item.bodyPartsCovered is undefined or empty, exclude the item
                }
                // Filter item.bodyPartsCovered to include only those parts that are also in selectedFilters
                const matchingParts = item.bodyPartsCovered.filter(part => selectedFilters.includes(part));

                // Check if the number of matching parts is equal to the length of both selectedFilters and item's bodyPartsCovered
                // This ensures an exact match: the item must cover exactly the selected parts, no more, no less
                return matchingParts.length === selectedFilters.length && matchingParts.length === item.bodyPartsCovered.length;
            });

            setFilteredItems(newFilteredItems);
        }
    }, [items, filters]); // Depend on items and filters


    return (
        <>
            <Typography>
                {t("detail.body.filters.heading")}
            </Typography>
            <FormGroup row>
                <FormControlLabel
                    control={<Checkbox checked={filters.HEAD} onChange={handleFiltersChange} name="HEAD" size="small" />}
                    label={t("detail.body.filters.HEAD")}
                />
                <FormControlLabel
                    control={<Checkbox checked={filters.CHEST} onChange={handleFiltersChange} name="CHEST" size="small" />}
                    label={t("detail.body.filters.CHEST")}
                />
                <FormControlLabel
                    control={<Checkbox checked={filters.STOMACH} onChange={handleFiltersChange} name="STOMACH" size="small" />}
                    label={t("detail.body.filters.STOMACH")}
                />
            </FormGroup>
            <FormGroup row>
                <FormControlLabel
                    control={<Checkbox checked={filters.LEFT_ARM} onChange={handleFiltersChange} name="LEFT_ARM" size="small" />}
                    label={t("detail.body.filters.LEFT_ARM")}
                />
                <FormControlLabel
                    control={<Checkbox checked={filters.RIGHT_ARM} onChange={handleFiltersChange} name="RIGHT_ARM" size="small" />}
                    label={t("detail.body.filters.RIGHT_ARM")}
                />
                <FormControlLabel
                    control={<Checkbox checked={filters.LEFT_LEG} onChange={handleFiltersChange} name="LEFT_LEG" size="small" />}
                    label={t("detail.body.filters.LEFT_LEG")}
                />
                <FormControlLabel
                    control={<Checkbox checked={filters.RIGHT_LEG} onChange={handleFiltersChange} name="RIGHT_LEG" size="small" />}
                    label={t("detail.body.filters.RIGHT_LEG")}
                />
            </FormGroup>
        </>
    );
};